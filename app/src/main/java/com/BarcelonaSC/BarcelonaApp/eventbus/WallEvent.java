package com.BarcelonaSC.BarcelonaApp.eventbus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonardojpr on 2/1/18.
 */

public class WallEvent {

    public static final String WALL_DELETE_POST = "WALL_DELETE_POST";
    public static final String WALL_LIKE_POST = "WALL_LIKE_POST";
    public static final String WALL_CREATE_COMMENT_POST = "WALL_CREATE_COMMENT_POST";
    public static final String WALL_DELETE_COMMENT_POST = "WALL_DELETE_COMMENT_POST";
    public static final String WALL_EDIT_POST = "WALL_EDIT_POST";
    public static final String WALL_CREATE__POST = "WALL_CREATE_POST";
    public static final String MOVE_WALL = "WALL_MOVE";
    public static final String WALL_EDIT_COMMENT_POST = "WALL_EDIT_COMMENT_POST";

    Map<String, Object> action;

    public static WallEvent newInstance(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new WallEvent(map);
    }

    public WallEvent(Map<String, Object> action) {
        this.action = action;
    }

    public Map<String, Object> getAction() {
        return action;
    }

    public void setAction(Map<String, Object> countComment) {
        this.action = action;
    }
}
