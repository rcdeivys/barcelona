package com.BarcelonaSC.BarcelonaApp.eventbus;

import com.BarcelonaSC.BarcelonaApp.models.WallItem;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallCreatePostEvent {

    public WallItem wallItem;

    public WallCreatePostEvent(WallItem wallItem) {
        this.wallItem = wallItem;
    }

    public WallItem getWallItem() {
        return wallItem;
    }

    public void setWallItem(WallItem wallItem) {
        this.wallItem = wallItem;
    }
}
