package com.BarcelonaSC.BarcelonaApp.eventbus;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallCreatePostEvent {

    public boolean create;

    public WallCreatePostEvent(boolean create) {
        this.create = create;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }
}
