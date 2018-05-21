package com.losingtimeapps.whitebrand.evenbus;

/**
 * Created by Carlos on 15/01/2018.
 */

public class ChooseOpenEvent {

    String move;
    boolean open=true;

    public ChooseOpenEvent(String move, boolean open) {
        this.move = move;
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public ChooseOpenEvent(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

}
