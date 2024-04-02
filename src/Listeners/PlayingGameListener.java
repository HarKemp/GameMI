package Listeners;

import Forms.PlayingForm;

public interface PlayingGameListener {
    void setPlayingForm(PlayingForm playingForm);
    boolean takeTurn(int move);
}