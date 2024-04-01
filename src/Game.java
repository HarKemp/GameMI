enum playerNames {
    HUMAN,
    COMPUTER
}

public class Game {

    // Cik dziļu grafu ģenerēt - cik gājienus uz priekšu
    // Ja šo uzliks par dziļu, tad varbūt nokārsies programma
    // Augstāk par 7 neiesaku likt
    static final private int MAX_DEPTH = 7;
    // Ik pēc cik gājieniem ģenerēt jaunu grafu
    // Var nomainīt uz mazāku skaitli, ja grib svaigāku informāciju
    static final private int VALID_TURNS = MAX_DEPTH - 2;
    private static final int LOWER_BOUND = 5; // Kad pabeigts jābūt 15
    private static final int UPPER_BOUND = 20; // Kad pabeigts jābūt 20
    private final int mode;
    private NumberString nS;
    private int[] playerScores;
    private int playerMove = 0;

    Game(int mode) {
        this.mode = mode;
    }

    void startNewGame() {
        gameSetup();
        play();
        printResults();
    }

    private void gameSetup() {
        setupPlayers();
        setupNumberString();
        determineFirstPlayer();
    }

    private void setupPlayers() {
        // Izveido masīvu ar katra spēlētāja punktu skaitu
        int playerCount = 2;
        playerScores = new int[playerCount];
    }

    private void setupNumberString() {
        // Noskaidro ciparu virknes garumu
        int length;
        System.out.print("Input number-string length: ");
        while(true) {
            length = InputReader.getInt();
            if (length > UPPER_BOUND || length < LOWER_BOUND) {
                System.out.println("Incorrectly defined number-string length");
                System.out.print("Try again: ");
                continue;
            }
            break;
        }

        // Izveido ciparu virkni
        nS = new NumberString(length);
    }

    private void determineFirstPlayer() {
        // Noskaidro kurš veiks pirmo gājiemu - cilvēks vai dators
        System.out.println("Who is going to take the first turn?");
        System.out.println("(c): Computer");
        System.out.println("(h): Human");
        char answer;
        while(true) {
            answer = InputReader.getChar();
            if (answer != 'c' && answer != 'h') {
                System.out.println("input-output error");
                System.out.print("Try again: ");
                continue;
            }
            break;
        }

        // Ja atbilde ir c, tad sāk dators (pagaidām spēlē identificē kā "Player 2", vēlāk var uztaisīt lai identificētu kā "dators").
        // Ja atbilde ir h, tad sāk cilvēks (pagaidām spēlē identificē kā "Player 1", vēlāk var uztaisīt lai identificētu kā "spēlētājs")
        if (answer == 'c') playerMove = 1;
    }

    private void play() {
        // Nosaka pašreizējo gājienu
        int currentTurn = 0;
        // Nosaka gājienu līdz kuram ir derīgs pašlaik uzģenerētais grafs
        int generatedUntilTurn = 0;
        // Uztaisa tukšu grafu kā placeholder
        Graph graph = new Graph();

        Node activeNode = null;
        Node bestEndNode = null;

        // Pilda gājienus līdz simbolu virkne ir tukša
        while(!nS.isEmpty()) {
            // Ģenerē grafu
            if (generatedUntilTurn <= currentTurn) {
                generatedUntilTurn = currentTurn + VALID_TURNS;
                graph = new Graph(playerScores, nS, MAX_DEPTH, currentTurn);
                activeNode = graph.getRootNode();
                bestEndNode = null;
            }

            if (playerMove == 1 && bestEndNode == null && activeNode != null)
                bestEndNode = alphaBeta(activeNode, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

            //TODO TEST - Izvada grafu terminālī (Beigās jāizņem ārā no koda)
            if (this.mode == 1) {
                graph.printGraph(VALID_TURNS - (generatedUntilTurn - currentTurn));
            }

            // Ja gājiena izpildes laikā notika kļūda (visdrīzāk ar ievadi), tad gājienu veic vēlreiz
            if (playerMove == 0){
                Node move = takeHumanTurn(activeNode);

                if(null == move)
                    continue;
                else
                    activeNode = move;
            }
            else {
                Node bestMove = bestEndNode;

                while(bestMove.getTurn() != (currentTurn + 1))
                    bestMove = bestMove.getParent();

                if (activeNode != bestMove.getParent()) {
                    bestEndNode = null;
                    continue;
                }

                Node move = takeComputerTurn(bestMove);

                if(null == move)
                    continue;
                else
                    activeNode = move;
            }

            // Pāriet uz nākamā spēlētāja gājienu
            playerMove = playerMove == 0 ? 1 : 0;
            currentTurn++;
        }

    }

    private Node takeComputerTurn(Node bestMove) {
        printStatus();

        int opponentPlayer =  playerMove == 0 ? 1 : 0;

        switch (bestMove.getMove()) {
            case 1:
            case 2:
            case 3:
            case 4:
                if(!Move.takeNumber(nS, playerScores, playerMove, bestMove.getMove())) {
                    return null;
                }
                break;
            case 5:
                if (!Move.splitNumber2(nS, playerScores, opponentPlayer)) {
                    return null;
                }
                break;
            case 6:
                if (!Move.splitNumber4(nS, playerScores, opponentPlayer)) {
                    return null;
                }
                break;
            default:
                return null;
        }

        return bestMove;
    }

    private Node alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || node.getChildren().isEmpty()) {
            return node;
        }

        Node bestNode = null;
        Node resultNode;
        int value;

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                resultNode = alphaBeta(child, depth - 1, alpha, beta, false);
                if (resultNode.getHeuristic() > value) {
                    value = resultNode.getHeuristic();
                    bestNode = resultNode;
                }
                node.setHeuristic(value);
                alpha = Math.max(alpha, value);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                resultNode =  alphaBeta(child, depth - 1, alpha, beta, true);
                if (resultNode.getHeuristic() < value) {
                    value = resultNode.getHeuristic();
                    bestNode = resultNode;
                }
                node.setHeuristic(value);
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }

        }

        return bestNode;
    }

    private Node takeHumanTurn(Node activeNode) {
        printStatus();

        // Veic gājienu
        System.out.println("Choose move:");
        System.out.println("(t): Take a number ");
        System.out.println("(2): Split a Two");
        System.out.println("(4): Split a Four");
        char answer = InputReader.getChar();
        int opponentPlayer;
        int move;
        switch (answer) {
            case 't':
                System.out.print("Input number you want to take: ");
                int number = InputReader.getInt();
                move = number;
                if (!Move.takeNumber(nS, playerScores, playerMove, number)) {
                    System.out.println("No " + number + "s in the string");
                    return null;
                }
                break;
            case '2':
                opponentPlayer = playerMove == 0 ? 1 : 0;
                move = 5;
                if (!Move.splitNumber2(nS, playerScores, opponentPlayer)) {
                    System.out.println("No twos in the string");
                    return null;
                }
                break;
            case '4':
                opponentPlayer = playerMove == 0 ? 1 : 0;
                move = 6;
                if (!Move.splitNumber4(nS, playerScores, opponentPlayer)) {
                    System.out.println("No fours in the string");
                    return null;
                }
                break;
            default:
                System.out.println("input-output error");
                return null;
        }

        return activeNode.getChildWithMove(move);
    }

    private void printStatus() {
        System.out.println("==========================================");
        System.out.println("Numbers: " + nS.convertToString());
        System.out.println(playerNames.HUMAN + " points: " + playerScores[0]);
        System.out.println(playerNames.COMPUTER + " points: " + playerScores[1]);
        System.out.println("==========================================");
        if (playerMove == 0)
            System.out.println(playerNames.HUMAN + " move!");
        else
            System.out.println(playerNames.COMPUTER + " move!");
    }

    private void printResults() {
        // Izvada spēles rezultātus
        System.out.println("Game over!");
        System.out.println(playerNames.HUMAN + " points: " + playerScores[0]);
        System.out.println(playerNames.COMPUTER + " points: " + playerScores[1]);
        if (playerScores[0] == playerScores[1]) {
            System.out.println("It's a tie!");
            return;
        }
        if (playerScores[0] > playerScores[1])
            System.out.println(playerNames.HUMAN + " wins");
        else
            System.out.println(playerNames.COMPUTER + " wins");
    }
}

