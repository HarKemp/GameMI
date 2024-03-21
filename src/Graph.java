import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private int MAX_DEPTH;
    private Node graph;

    Graph() {
        this.graph = null;
    }

    Graph(int[] playerScores, NumberString numberString, int maxDepth) {
        this.MAX_DEPTH = maxDepth;
        createGraph(playerScores, numberString);
    }

    Node getRootNode() {
        return graph;
    }

    private void createGraph(int[] playerScores, NumberString numberString) {
        this.graph = new Node(playerScores, numberString, this.graph, 0);
        int turn = 1;
        addNodes(playerScores, numberString, turn, this.graph);
    }

    private Node createNewNode(NumberString numberString, int[] playerScores, Node parentNode, int turn) {
        // Glabās pievienojamo virsotni
        Node newNode = new Node(playerScores, numberString, parentNode, turn);
        parentNode.addChild(newNode);
        return newNode;
    }

    private int[] copyOfArray(int[] originalArray) {
        int[] copyArray = new int[originalArray.length];
        System.arraycopy(originalArray, 0, copyArray, 0, originalArray.length);
        return copyArray;
    }

    private void addNodes(int[] playerScores, NumberString numberString, int turn, Node currentNode) {
        // Glabās pilnu kopiju skaitļu virknei
        NumberString copyOfNS;
        // Glabās pilnu kopiju spēlētāju punktu sadalījumam
        int[] copyOfPS;

        if (turn > MAX_DEPTH) return;

        // Kuram jāveic gājiens?
        // Ja pāra gājiens, tad cilvēks
        // Ja nepāra gājiens, tad dators
        int currentPlayer = (turn % 2) == 0 ? 0 : 1;
        int opponentPlayer = (turn % 2) == 0 ? 1 : 0;

        for (int i = 1; i < 7; i++) {
            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            // Ja ņem ciparu 1-4
            if (i < 5 && Move.takeNumber(copyOfNS, copyOfPS, currentPlayer, i)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn);
                addNodes(copyOfPS, copyOfNS, turn + 1, newNode);
            }
            // Ja dala 2
            else if (i == 5 && Move.splitNumber2(copyOfNS, copyOfPS, opponentPlayer)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn);
                addNodes(copyOfPS, copyOfNS, turn + 1, newNode);
            }
            // Ja dala 4
            else if (i == 6 && Move.splitNumber4(copyOfNS, copyOfPS, opponentPlayer)) {
                Node newNode = createNewNode(copyOfNS, copyOfPS, currentNode, turn);
                addNodes(copyOfPS, copyOfNS, turn + 1, newNode);
            }
        }
    }


    //TODO TEST -> No ChatGPT
    void printGraph(int turn) {
        if (this.graph == null) return; // If the graph is empty, return immediately

        Queue<Node> queue = new LinkedList<>(); // Use a queue to keep track of the nodes to visit
        queue.add(this.graph); // Start with the root node

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll(); // Remove the head of the queue

            if (currentNode.getTurn() >= turn) {
                // Determine a fixed width for the NumberString. Adjust the value of 20 as needed
                String numberStringFormatted = String.format("%-50s", currentNode.getNumberString().convertToString());

                // Print current node's details with fixed spacing
                System.out.printf("Turn: %-3d; Numbers: %s; Hum: %-3d; Comp: %-3d; Heuristic: %-3d%n",
                        currentNode.getTurn(),
                        numberStringFormatted,
                        currentNode.getPlayerScores()[0],
                        currentNode.getPlayerScores()[1],
                        currentNode.getHeuristic());
            }

            // Add all the children of the current node to the queue
            List<Node> children = currentNode.getChildren(); // Assume this method exists
            if (children != null) {
                queue.addAll(children);
            }
        }
    }
}