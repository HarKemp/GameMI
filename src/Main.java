import Forms.StartForm;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        StartForm startForm = new StartForm(game);

        startForm.setLocationRelativeTo(null);
        startForm.setVisible(true);
    }
}