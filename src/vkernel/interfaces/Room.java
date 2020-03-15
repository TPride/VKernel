package vkernel.interfaces;

import vkernel.includes.RoomState;
import java.util.LinkedList;

/**
 * Created by TPride on 2020/2/22.
 */
public class Room {
    protected Level level;
    public RoomState state = RoomState.NOT;

    public  Room() {

    }

    public Level getRoomLevel() {
        return level;
    }
}