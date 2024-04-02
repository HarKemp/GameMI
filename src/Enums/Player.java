package Enums;

public enum Player {
    Human(0), Computer(1);
    private final int value;
    Player(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
