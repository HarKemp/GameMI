import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    final int human = 0;
    final int computer = 1;

    Node graph;
    Graph(int[] playerScores, NumberString numberString) {
        createGraph(playerScores, numberString);
    }

    void createGraph(int[] playerScores, NumberString numberString) {
        this.graph = new Node(playerScores, numberString, this.graph, 0);
        int turn = 1;
        addNodes(playerScores, numberString, turn, this.graph);
    }



    void addNodes(int[] playerScores, NumberString numberString, int turn, Node currentNode) {
        // Glabās pilnu kopiju skaitļu virknei
        NumberString copyOfNS;
        // Glabās pilnu kopiju spēlētāju punktu sadalījumam
        int[] copyOfPS;
        // Glabās pievienojamo virsotni
        Node newNode;

        // Ja šis ir datoram paredzētais gājiens
        if (turn % 2 == 0) {
            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.takeNumber(copyOfNS, copyOfPS, this.computer, 1)) {

                //TODO pārlikt uz ārēju funkciju
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                //

                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }

            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.takeNumber(copyOfNS, copyOfPS, this.computer, 2)) {
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }

            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.takeNumber(copyOfNS, copyOfPS, this.computer, 3)) {
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }

            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.takeNumber(copyOfNS, copyOfPS, this.computer, 4)) {
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }

            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.splitNumber2(copyOfNS, copyOfPS, this.human)) {
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }

            copyOfNS = numberString.createCopy();
            copyOfPS = copyOfArray(playerScores);
            if (Move.splitNumber4(copyOfNS, copyOfPS, this.human)) {
                newNode = new Node(copyOfPS, copyOfNS, currentNode, turn);
                currentNode.addChild(newNode);
                addNodes(copyOfPS, copyOfNS, ++turn, newNode);
            }


        }
        // Ja cilvēkam paredzētais gājiens
        else {

            // TODO
            // Pieņemt visloģiskāko gājienu (būs mazāk virsotnes jāģenerē un varēs taisīt dziļāku koku),
            // bet būs jāģenerē jauns koks katru reizi, kad cilvēks veiks neloģisku gājienu
            // vai
            // Ģenerēt visas iespējamās virsotnes arī cilvēka spēlētājam, (būs jāģenerē vairāk virsotņu
            // un līdz ar to būs mazāks grafa dziļums)
            // , bet nebūs jāģenerē jauns koks ar katru neloģisku cilvēka gājienu

            // Varbūt var implementēt visloģiskākās virsotnes atrašanu ar PriorityQueue un @override uz compareTo metodes
            // , lai salīdzināšana strādātu ar mūsu Node objektu heiristiskajiem novērtējumiem

            // Ja implementē visas iespējamās virsotnes,
            // tad to var darīt ļoti līdzīgi kā datora gājienu virsotņu ģenerēšanu
        }
    }

    int[] copyOfArray(int[] originalArray) {
        int[] copyArray = new int[originalArray.length];
        System.arraycopy(originalArray, 0, copyArray, 0, originalArray.length );
        return copyArray;
    }

}
