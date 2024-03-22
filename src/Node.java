import java.util.LinkedList;
import java.util.List;

public class Node {
    private int[] playerScores;
    private int[] numberString;
    // Saraksts ar tiešajiem, šīs virsotnes, bērniem
    private final List<Node> childList = new LinkedList<Node>();
    // Saraksts ar tiešajiem, šīs virsotnes, vecākiem
    private final List<Node> parentList = new LinkedList<Node>();

    // Heiristiskās funkcijas novērtējums konkrētajai virsotnei
    // Noklusējumā -1 (ja šī virsotne nav izveidota datora gājiena rezultātā)
    private int heuristic = -1;
    // Relatīvi noteiktais gājiens pēc kārtas konkrētajā grafā
    private int turn;

    Node () {}
    Node (int[] playerScores, NumberString numberString, Node parent, int turn) {
        this.playerScores = playerScores;
        this.numberString = numberString.convertToStaticArray();
        this.parentList.add(parent);
        this.turn = turn;
        //generateHeuristic();
    }

    // Heiristiskā funkcija
    private void generateHeuristic() {
        this.heuristic = this.playerScores[1] - this.playerScores[0];
    }

    Node addChild(Node child) {
        this.childList.add(child);
        return child;
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

    // Atgriež konkrētu virsotnes bērnu
    Node getChild(int childIndex) {
        return childList.get(childIndex);
    }

    // Atgriež visu virsotnes bērnu sarakstu
    List<Node> getChildren() {
        return childList;
    }

    List<Node> getParents() {
        return parentList;
    }

    Node getParent() {
        return parentList.get(0);
    }

}
