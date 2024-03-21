public class Game {

    // Cik dziļu grafu ģenerēt - cik gājienus uz priekšu
    // Ja šo uzliks par dziļu, tad varbūt nokārsies programma
    // Augstāk par 7 neiesaku likt
    static final private int MAX_DEPTH = 3;
    // Ik pēc cik gājieniem ģenerēt jaunu grafu
    // Var nomainīt uz mazāku skaitli, ja grib svaigāku informāciju
    static final private int VALID_TURNS = MAX_DEPTH;

    enum playerNames {
        HUMAN,
        COMPUTER
    }


    static void startNewGame(int mode) {
        gameSetup(mode);
    }


    private static void gameSetup(int mode) {
        // Testēšanas laikā var samazināt lowerBound
        // Nosaka ierobežojumus skaitļu virknes garumam
        int lowerBound = 5; // Kad pabeigts jābūt 15
        int upperBound = 20; // Kad pabeigts jābūt 20


        // Izveido masīvu ar katra spēlētāja punktu skaitu
        int playerCount = 2;
        int[] playerScores = new int[playerCount];
//        for (int i = 0; i < playerCount; i++) {
//            playerScores[i] = 0;
//        }


        // Noskaidro ciparu virknes garumu
        int length;
        System.out.print("Input number-string length: ");
        while(true) {
            length = InputReader.getInt();
            if (length > upperBound || length < lowerBound) {
                System.out.println("Incorrectly defined number-string length");
                System.out.print("Try again: ");
                continue;
            }
            break;
        }


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
        int playerMove = 0;
        if (answer == 'c') playerMove = 1;


        // Izveido ciparu virkni
        NumberString nS = new NumberString(length);

        // Nosaka pašreizējo gājienu
        int currentTurn = 0;
        // Nosaka gājienu līdz kuram ir derīgs pašlaik uzģenerētais grafs
        int generatedUntilTurn = 0;
        // Uztaisa tukšu grafu kā placeholder
        Graph graph = new Graph();

        // Pilda gājienus līdz simbolu virkne ir tukša
        while(!nS.isEmpty()) {
            // Ģenerē grafu
            if (playerMove == 1 && generatedUntilTurn <= currentTurn) {
                generatedUntilTurn = currentTurn + VALID_TURNS;
                graph = new Graph(playerScores, nS, MAX_DEPTH);
            }

            //TODO TEST - Izvada grafu terminālī (Beigās jāizņem ārā no koda)
            if (mode == 1) {
                graph.printGraph(VALID_TURNS - (generatedUntilTurn - currentTurn));
            }

            // Ja gājiena izpildes laikā notika kļūda (visdrīzāk ar ievadi), tad gājienu veic vēlreiz
            if (!takeTurn(nS, playerScores, playerMove, graph)) continue;
            // Pāriet uz nākamā spēlētāja gājienu
            playerMove = playerMove == 0 ? 1 : 0;
            currentTurn++;
        }


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

    static boolean takeTurn(NumberString nS, int[] playerScores, int playerMove, Graph graph) {
        if (playerMove == 0)
            return takeHumanTurn(nS, playerScores, playerMove);
        else
            return takeHumanTurn(nS, playerScores, playerMove);
            //TODO Pēc tam aizvietot ar
            //return takeComputerTurn(nS, playerScores, playerMove, graph);
    }

    static boolean takeComputerTurn(NumberString nS, int[] playerScores, int playerMove, Graph graph) {
        //TODO - Izvēlēties datora veicamo gājienu

        // Kā piekļūt grafa virsotnēm
        //graph.getRootNode().getChild(0); // Iegūt konkrētās virsotnes n-to bērnu
        //graph.getRootNode().getChildren(); // Iegūt konkrētās virsotnes bērnu sarakstu
        // graph.getRootNode().getHeuristic(); // Iegūt virsotnes heiristisko novērtējumu
        // u.c.

        return false;
    }

    static boolean takeHumanTurn(NumberString nS, int[] playerScores, int playerMove) {

        // Izvada informāciju par spēles pašreizējo stāvokli
        System.out.println("==========================================");
        System.out.println("Numbers: " + nS.convertToString());
        System.out.println(playerNames.HUMAN + " points: " + playerScores[0]);
        System.out.println(playerNames.COMPUTER + " points: " + playerScores[1]);
        System.out.println("==========================================");
        if (playerMove == 0)
            System.out.println(playerNames.HUMAN + " move!");
        else
            System.out.println(playerNames.COMPUTER + " move!");

        // Veic gājienu
        System.out.println("Choose move:");
        System.out.println("(t): Take a number ");
        System.out.println("(2): Split a Two");
        System.out.println("(4): Split a Four");
        char answer = InputReader.getChar();
        int opponentPlayer;
        switch (answer) {
            case 't':
                System.out.print("Input number you want to take: ");
                int number = InputReader.getInt();
                if (!Move.takeNumber(nS, playerScores, playerMove, number)) {
                    System.out.println("No " + number + "s in the string");
                    return false;
                }
                break;
            case '2':
                opponentPlayer = playerMove == 0 ? 1 : 0;
                if (!Move.splitNumber2(nS, playerScores, opponentPlayer)) {
                    System.out.println("No twos in the string");
                    return false;
                }
                break;
            case '4':
                opponentPlayer = playerMove == 0 ? 1 : 0;
                if (!Move.splitNumber4(nS, playerScores, opponentPlayer)) {
                    System.out.println("No fours in the string");
                    return false;
                }
                break;
            default:
                System.out.println("input-output error");
                return false;
        }
        return true;

    }
}

