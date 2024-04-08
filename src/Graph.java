import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private final int MAX_DEPTH;
    private final Node graph;

    Graph(int[] playerScores, NumberString numberString, int maxDepth, int turn, int playerMove) {
        this.MAX_DEPTH = maxDepth;
        this.graph = new Node(playerScores, numberString, null, turn, 0);
        addNodes(playerScores, numberString, this.graph, playerMove);
        generateHeuristic(this.graph);
    }

    Node getRootNode() {
        return graph;
    }

    private Node createNewNode(NumberString numberString, int[] playerScores, Node parentNode, int turn, int move) {
        // Glabās pievienojamo virsotni
        Node newNode = new Node(playerScores, numberString, parentNode, turn, move);
        parentNode.addChild(newNode);
        return newNode;
    }

    private int[] copyOfArray(int[] originalArray) {
        int[] copyArray = new int[originalArray.length];
        System.arraycopy(originalArray, 0, copyArray, 0, originalArray.length);
        return copyArray;
    }

    private void addNodes(int[] playerScores, NumberString numberString, Node currentNode, int playerMove) {
        // Glabās pilnu kopiju skaitļu virknei
        NumberString copyOfNS;
        // Glabās pilnu kopiju spēlētāju punktu sadalījumam
        int[] copyOfPS;

        int turn = currentNode.getTurn() + 1;

        if ((turn - this.graph.getTurn()) > MAX_DEPTH) return;

        // Nosaka kuram jāveic gājiens
        int currentPlayer = playerMove == 0 ? 0 : 1;
        int opponentPlayer = playerMove == 0 ? 1 : 0;

        for (int i = 1; i < 7; i++) {
            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            // Ja ņem ciparu 1-4
            if (i < 5 && Move.takeNumber(copyOfNS, copyOfPS, currentPlayer, i)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn, i);
                addNodes(copyOfPS, copyOfNS, newNode, (playerMove == 0 ? 1 : 0));
            }
            // Ja dala 2
            else if (i == 5 && Move.splitNumber2(copyOfNS, copyOfPS, opponentPlayer)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn, i);
                addNodes(copyOfPS, copyOfNS, newNode, (playerMove == 0 ? 1 : 0));
            }
            // Ja dala 4
            else if (i == 6 && Move.splitNumber4(copyOfNS, copyOfPS, opponentPlayer)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn, i);
                addNodes(copyOfPS, copyOfNS, newNode, (playerMove == 0 ? 1 : 0));
            }
        }
    }

    // Ģenerē grafam heiristiskos novērtējumus
    void generateHeuristic(Node currentNode) {
        Queue<Node> endNodes = new LinkedList<>();
        findEndStates(currentNode, endNodes);
        for (Node node : endNodes) {
            assignHeuristic(node);
        }
    }

    // Atrod uzvarošās strupceļa virsotnes ar no datiem virzīto pārmeklēšanu dziļumā
    void findEndStates(Node currentNode, Queue<Node> endNodes) {
        if (currentNode.getChildren().isEmpty()) {
            endNodes.add(currentNode);
            return;
        }

        for (Node node : currentNode.getChildren()) {
            findEndStates(node, endNodes);
        }
    }

    // Piešķir virsotnēm heiristiskos novērtējumus ar no mērķa virzīto pārmeklēšanu dziļumā
    void assignHeuristic(Node currentNode) {
        // Gala virsotnei piešķir heiristisko novērtējumu
        currentNode.setHeuristic(heuristicFunction(currentNode));
    }

    // Definē heiristisko funkciju
    int heuristicFunction(Node currentNode) {
        int heuristic = 0;
        int humanScore = currentNode.getPlayerScores()[0];
        int computerScore = currentNode.getPlayerScores()[1];

        heuristic += computerScore;
        heuristic += (computerScore - humanScore) * 10;

        return heuristic;

    }
}