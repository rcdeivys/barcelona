package com.millonarios.MillonariosFC.eventbus;

/**
 * Created by Leonardojpr on 12/9/17.
 */

public class PlayerEvent {

    public boolean update;

    public PlayerEvent(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
