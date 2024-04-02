import java.util.LinkedList;
import java.util.List;

public class Node {
    private final int[] playerScores;
    private final int[] numberString;
    // Saraksts ar tiešajiem, šīs virsotnes, bērniem
    private final List<Node> childList = new LinkedList<>();
    // Saraksts ar tiešajiem, šīs virsotnes, vecākiem
    private final List<Node> parentList = new LinkedList<>();

    // Noklusējumā visszemākais iespējamais int vērtējums (ja šī virsotne nav gala virsotne)
    private int heuristic = Integer.MIN_VALUE;
    // Relatīvi noteiktais gājiens pēc kārtas konkrētajā grafā
    private int turn;
    private final int move;

    Node (int[] playerScores, NumberString numberString, Node parent, int turn, int move) {
        this.playerScores = playerScores;
        this.numberString = numberString.convertToStaticArray();
        this.parentList.add(parent);
        this.turn = turn;
        this.move = move;
        //generateHeuristic();
    }

    // Heiristiskā funkcija
    private void generateHeuristic() {
        this.heuristic = this.playerScores[1] - this.playerScores[0];
    }

    void addChild(Node child) {
        this.childList.add(child);
    }

    NumberString getNumberString() {
        return new NumberString(this.numberString);
    }

    int[] getPlayerScores() {
        return playerScores;
    }

    void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    int getHeuristic() {
        return this.heuristic;
    }

    void setTurn(int turn) {
        this.turn = turn;
    }

    int getTurn() {
        return this.turn;
    }

    int getMove() {
        return this.move;
    }

    // Atgriež konkrētu virsotnes bērnu
    Node getChild(int childIndex) {
        return childList.get(childIndex);
    }

    Node getChildWithMove(int move) {
        for (Node node : childList) {
            if (node.move == move) {
                return node;
            }
        }
        return null;
    }

    // Atgriež visu virsotnes bērnu sarakstu
    List<Node> getChildren() {
        return childList;
    }

    List<Node> getParents() {
        return parentList;
    }

    Node getParent() {
        return parentList.getFirst();
    }
}
