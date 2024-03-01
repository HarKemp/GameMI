
public class Move {

    // Sadala virknē doto ciparu divos vienādos ciparos
    static private boolean splitNumber(NumberString nS, int digitToSplit) {
        int index = nS.findIndexOf(digitToSplit);
        if (index == -1) {
//            System.out.println("Digit not in string");
            return false;
        }
        nS.replaceDigit(index, digitToSplit/2);
        nS.addDigit(index,digitToSplit/2);
        return true;
    }


    // Paņem ciparu no virknes
    static boolean takeNumber(NumberString nS, int[] playerScores, int playerIndex, int tookDigit) {
        if(nS.findIndexOf(tookDigit) == -1) {
//            System.out.println("Digit not in string");
            return false;
        }
        playerScores[playerIndex] += nS.remove(tookDigit);
        return true;
    }


    // Sadala ciparu 2 un pieskaita pretiniekam 1 punktu
    static boolean splitNumber2(NumberString nS, int[] playerScores, int opponentPlayerIndex) {
        if (splitNumber(nS,2)) {
            playerScores[opponentPlayerIndex] += 1;
            return true;
        }
        return false;
    }


    // Sadala ciparu 4 un atņem pretiniekam 1 punktu
    static boolean splitNumber4(NumberString nS, int[] playerScores, int opponentPlayerIndex) {
        if (splitNumber(nS,4)) {
            playerScores[opponentPlayerIndex] -= 1;
            return true;
        }
        return false;
    }

}