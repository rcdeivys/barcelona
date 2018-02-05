package com.millonarios.MillonariosFC.eventbus;

/**
 * Created by Leonardojpr on 2/1/18.
 */

public class WallCreateCommentEvent {
    private int countComment;

    public WallCreateCommentEvent(int countComment) {
        this.countComment = countComment;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }
}
