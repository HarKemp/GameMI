import Enums.AlgorithmType;
import Enums.GameStatus;
import Enums.Player;
import Forms.PlayingForm;
import Forms.StartForm;
import Listeners.PlayingGameListener;
import Listeners.StartGameListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements StartGameListener, PlayingGameListener {

    // Cik dziļu grafu ģenerēt - cik gājienus uz priekšu
    // Ja šo uzliks par dziļu, tad varbūt nokārsies programma
    // Augstāk par 7 neiesaku likt
    static final private int MAX_DEPTH = 7;
    // Ik pēc cik gājieniem ģenerēt jaunu grafu
    // Var nomainīt uz mazāku skaitli, ja grib svaigāku informāciju
    static final private int VALID_TURNS = MAX_DEPTH - 2;
    private static final int LOWER_BOUND = 5; // Kad pabeigts jābūt 15
    private static final int UPPER_BOUND = 20; // Kad pabeigts jābūt 20

    private AlgorithmType algorithmType;
    private NumberString nS;
    private int[] playerScores;
    private Player playerMove;
    private GameStatus status = GameStatus.Initialized;
    private PlayingForm playingForm;
    private StartForm startForm;
    private int currentTurn;
    private int generatedUntilTurn;
    private Graph graph;
    private Node activeNode;
    private Node bestEndNode;

    // Nosaka cik virsotnes pa visam tika ģenerētas ar grafa veidošanas algoritmu
    private int generatedNodeCount;
    // Nosaka cik lietderīgas/unikālas virsotnes tika ģenerētas
    private int graphNodeCount;
    // Nosaka cik virsotnes tika apmeklētas ar izvēlēto algoritmu
    private int visitedNodeCount;

    public void setPlayingForm(PlayingForm playingForm) {
        this.playingForm = playingForm;
    }

    public void setStartForm(StartForm startForm) {
        this.startForm = startForm;
    }

    public void startNewGame(int length, Player firstPlayer, AlgorithmType algorithm) {
        if (!isValidLength(length))
            return;

        if (status != GameStatus.Initialized && status != GameStatus.Ended)
            return;

        status = GameStatus.Playing;

        // Izveido masīvu ar katra spēlētāja punktu skaitu
        int playerCount = 2;
        playerScores = new int[playerCount];

        // Izveido ciparu virkni
        nS = new NumberString(length);

        generatedNodeCount = 0;
        // Viens apzīmē sākotnējo (saknes) virsotni
        graphNodeCount = 1;
        visitedNodeCount = 0;

        bestEndNode = null;
        playerMove = firstPlayer;
        algorithmType = algorithm;
        currentTurn = 0;
        generatedUntilTurn = 0;

        PlayingForm playingForm = new PlayingForm(this, startForm);

        playingForm.setStatus(nS.convertToString(), playerScores[0], playerScores[1], playerMove, 0,
                visitedNodeCount, generatedNodeCount, graphNodeCount);

        if (playerMove == Player.Computer)
            takeTurn(-1);
    }

    public boolean isValidLength(int length) {
        return LOWER_BOUND <= length && length <= UPPER_BOUND;
    }

    public boolean takeTurn(int move) {
        if (status != GameStatus.Playing)
            return false;

        long startTime = System.nanoTime();

        //Ģenerē grafu
        if (generatedUntilTurn <= currentTurn) {
            generatedUntilTurn = currentTurn + VALID_TURNS;
            graph = new Graph(playerScores, nS, MAX_DEPTH, currentTurn, playerMove.getValue());
            activeNode = graph.getRootNode();
            generatedNodeCount += graph.getNodeCount();
        }

        // Pieskaita kopējam virsotņu skaitam visu NĀKAMĀ līmeņa virsotņu skaitu
        graphNodeCount += activeNode.getChildren().size();

        if (playerMove == Player.Human){
            if (takeMove(move))
                return false;
            activeNode = activeNode.getChildWithMove(move);
        }
        else {
            Node bestMove = getBestMove();

            if (activeNode != bestMove.getParent()) {
                bestEndNode = null;
                bestMove = getBestMove();
            }

            if (takeMove(bestMove.getMove()))
                return false;

            activeNode = bestMove;
        }

        playerMove = getOponentPlayer();
        currentTurn++;

        playingForm.setStatus(nS.convertToString(), playerScores[0], playerScores[1], playerMove,
                System.nanoTime() - startTime, visitedNodeCount, generatedNodeCount, graphNodeCount);

        if (nS.isEmpty()) {
            status = GameStatus.Ended;
            printResults();
        }

        return true;
    }

    private Node getBestMove() {
        if (bestEndNode == null)
            generateBestEndNode();

        Node bestMove = bestEndNode;

        while(bestMove.getTurn() != (currentTurn + 1))
            bestMove = bestMove.getParent();

        return bestMove;
    }

    private void generateBestEndNode() {
        if (algorithmType == AlgorithmType.AlphaBeta)
            bestEndNode = alphaBeta(activeNode, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        else
            bestEndNode = minimax(activeNode, MAX_DEPTH, true);
    }

    private boolean takeMove(int move) {
        Player opponentPlayer = getOponentPlayer();

        switch (move) {
            case 1:
            case 2:
            case 3:
            case 4:
                if(!Move.takeNumber(nS, playerScores, playerMove.getValue(), move)) {
                    return true;
                }
                break;
            case 5:
                if (!Move.splitNumber2(nS, playerScores, opponentPlayer.getValue())) {
                    return true;
                }
                break;
            case 6:
                if (!Move.splitNumber4(nS, playerScores, opponentPlayer.getValue())) {
                    return true;
                }
                break;
            default:
                return true;
        }

        return false;
    }

    private Node alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {

        visitedNodeCount++;

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

    private Node minimax(Node node, int depth, boolean maximizingPlayer) {

        visitedNodeCount++;

        if (depth == 0 || node.getChildren().isEmpty()) {
            return node;
        }

        Node bestNode = null;
        Node resultNode;
        int value;

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                resultNode = minimax(child, depth - 1, false);
                if (resultNode.getHeuristic() > value) {
                    value = resultNode.getHeuristic();
                    bestNode = resultNode;
                }
                node.setHeuristic(value);
            }
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                resultNode = minimax(child, depth - 1, true);
                if (resultNode.getHeuristic() < value) {
                    value = resultNode.getHeuristic();
                    bestNode = resultNode;
                }
                node.setHeuristic(value);
            }
        }

        return bestNode;
    }

    private void printResults() {
        if (status != GameStatus.Ended)
            return;

        List<Player> winners = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        for (int score : playerScores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }

        for (Player player : Player.values()) {
            int ix = player.getValue();
            if (playerScores[ix] == maxScore) {
                winners.add(player);
            }
        }

        playingForm.setResults(winners.toArray(new Player[0]));
    }

    private Player getOponentPlayer() {
        return playerMove == Player.Human ? Player.Computer : Player.Human;
    }
}

