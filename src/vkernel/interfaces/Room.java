package vkernel.interfaces;

import vkernel.includes.RoomState;
import java.util.LinkedList;

/**
 * Created by TPride on 2020/2/22.
 */
public abstract class Room {
    private Level roomLevel;
    public LinkedList<String> players = new LinkedList<>();
    public RoomState state = RoomState.NOT;

    public Room(Level level) {
        this.roomLevel = level;
    }

    public LinkedList<String> getPlayers() {
        return players;
    }

    public Level getRoomLevel() {
        return roomLevel;
    }

    /**
     * 开始计时器
     * @return Boolean
     */
    public abstract boolean startTask();
}