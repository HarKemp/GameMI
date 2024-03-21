public class Main {
    public static void main(String[] args) {

        while(true) {
            // Sāk jaunu spēli
            System.out.println("Choose mode:");
            System.out.println("Don't print graph: (0)");
            System.out.println("Print graph: (1)");
            System.out.println("Note: The turn indicated in the output will be relative for the CURRENTLY generated graph");
            System.out.println("If the graph MAX_DEPTH is larger than 5 - it is recommended to not use (1)");
            int mode = InputReader.getInt();
            if (mode != 1 && mode != 0) continue;
            Game.startNewGame(mode);
            System.out.println("\n\n\nDo you want to start a new game? (y/n)");
            if (InputReader.getChar() == 'y') continue;
            break;
        }
    }
}