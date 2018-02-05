package com.millonarios.MillonariosFC.eventbus;

/**
 * Created by Carlos on 16/01/2018.
 */

public class ChooseUpdateRanEvent {


    boolean updateRanking;

    public boolean isUpdateRanking() {
        return updateRanking;
    }

    public ChooseUpdateRanEvent(boolean updateRanking) {
        this.updateRanking = updateRanking;
    }

    public void setUpdateRanking(boolean updateRanking) {
        this.updateRanking = updateRanking;
    }

}
