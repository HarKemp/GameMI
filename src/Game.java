public class Game {

    static void startNewGame() {
        gameSetup();
    }

    private static void gameSetup() {
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


        // Pilda gājienus līdz simbolu virkne ir tukša
        while(!nS.isEmpty()) {
            // Ja gājiena izpildes laikā notika kļūda (visdrīzāk ar ievadi), tad gājienu veic vēlreiz
            if (!takeTurn(nS, playerScores, playerMove)) continue;
            // Pāriet uz nākamā spēlētāja gājienu
            playerMove = playerMove == 0 ? 1 : 0;
        }


        // Izvada spēles rezultātus
        System.out.println("Game over!");
        System.out.println("Player 1 points: " + playerScores[0]);
        System.out.println("Player 2 points: " + playerScores[1]);
        if (playerScores[0] == playerScores[1]) {
            System.out.println("It's a tie!");
            return;
        }
        System.out.println("Player " + (playerScores[0] > playerScores[1] ? 1 : 2) + " wins");
    }


    static boolean takeTurn(NumberString nS, int[] playerScores, int playerMove) {

        // Izvada informāciju par spēles pašreizējo stāvokli
        System.out.println("==========================================");
        System.out.println("Numbers: " + nS.convertToString());
        System.out.println("Player 1 points: " + playerScores[0]);
        System.out.println("Player 2 points: " + playerScores[1]);
        System.out.println("==========================================");
        System.out.println("Player " + (playerMove + 1) + " move!");


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

