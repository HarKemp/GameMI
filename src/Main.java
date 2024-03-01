public class Main {
    public static void main(String[] args) {

        while(true) {
            // Sāk jaunu spēli
            Game.startNewGame();
            System.out.println("\n\n\nDo you want to start a new game? (y/n)");
            if (InputReader.getChar() == 'y') continue;
            break;
        }
    }
}