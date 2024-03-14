import java.util.LinkedList;
import java.util.List;

public class Node {
    private int[] playerScores;
    private int[] numberString;
    // Saraksts ar tiešajiem, šīs virsotnes, bērniem
    private List<Node> childList = new LinkedList<Node>();
    // Saraksts ar tiešajiem, šīs virsotnes, vecākiem
    private List<Node> parentList = new LinkedList<Node>();

    // Heiristiskās funkcijas novērtējums konkrētajai virsotnei
    private int heuristic = 1;
    // Relatīvi noteiktais gājiens pēc kārtas konkrētajā grafā
    private int turn;

    Node () {}
    Node (int[] playerScores, NumberString numberString, Node parent, int turn) {
        this.playerScores = playerScores;
        this.numberString = numberString.convertToStaticArray();
        this.parentList.add(parent);
        this.turn = turn;
    }

    Node addChild(Node child) {
        this.childList.add(child);
        return child;
    }

    NumberString getNumberString() {
        return new NumberString(this.numberString);
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

    Node getChild(int childIndex) {
        return childList.get(childIndex);
    }

}
