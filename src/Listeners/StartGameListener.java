package Listeners;

import Enums.AlgorithmType;
import Enums.Player;
import Forms.StartForm;

public interface StartGameListener {
    void setStartForm(StartForm form);
    boolean isValidLength(int length);
    void startNewGame(int length, Player firstPlayer, AlgorithmType algorithmType);
}