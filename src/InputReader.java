import java.util.Scanner;

public class InputReader {
    private static Scanner sc = new Scanner(System.in);

    static int getInt() {
        int number;
        while(true) {
            try {
                number = sc.nextInt();
                sc.nextLine();
            } catch (Exception E) {
                System.out.println("Input-output error");
                System.out.print("Try again: ");
                sc.nextLine();
                continue;
            }
            break;
        }
        return number;
    }

    static char getChar() {
        char symbol;
        while (true) {
            try {
                symbol = sc.nextLine().toLowerCase().charAt(0);
            } catch (Exception E) {
                System.out.println("input-output error");
                System.out.print("Try again: ");
                continue;
            }
            break;
        }
        return symbol;
    }
}
