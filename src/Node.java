import java.util.LinkedList;
import java.util.List;

public class Node {
    // Masīvs ar spēlētāju punktu skaitie
    private final int[] playerScores;
    // Masīvs, kas satur skaitļu virkni
    private final int[] numberString;
    // Saraksts ar tiešajiem, šīs virsotnes, bērniem
    private final List<Node> childList = new LinkedList<>();
    // Atsauce uz virsotnes priekšteci
    private final Node parent;
    // Noklusējumā heiristiskais novērtējums būs
    // visszemākais iespējamais int vērtējums (ja šī virsotne nav gala virsotne)
    private int heuristic = Integer.MIN_VALUE;
    // Gājiens pēc kārtas konkrētajā spēlē
    private final int turn;
    // Nosaka gājienu, kāds tika veikts, lai iegūtu šajā virsotnē glabāto spēles stāvokli (1 - 6)
    private final int move;

    Node (int[] playerScores, NumberString numberString, Node parent, int turn, int move) {
        this.playerScores = playerScores;
        this.numberString = numberString.convertToStaticArray();
        this.parent = parent;
        this.turn = turn;
        this.move = move;
    }

    void addChild(Node child) {
        this.childList.add(child);
    }

    int[] getPlayerScores() {
        return playerScores;
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

    int getTurn() {
        return this.turn;
    }

    int getMove() {
        return this.move;
    }

    // Atgriež to pēcteci, kas atbilst konkrēta gājiena rezultātā radītajam stāvoklim
    Node getChildWithMove(int move) {
        for (Node node : childList) {
            if (node.move == move) {
                return node;
            }
        }
        return null;
    }

    List<Node> getChildren() {
        return childList;
    }

    Node getParent() {
        return parent;
    }
}
