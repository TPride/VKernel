package vkernel.interfaces;

import vkernel.includes.RoomState;
import java.util.LinkedList;

/**
 * Created by TPride on 2020/2/22.
 */
public class Room {
    private Level roomLevel;
    public LinkedList<String> players = new LinkedList<>();
    public RoomState state = RoomState.NOT;

    public Room() {

    }

    public LinkedList<String> getPlayers() {
        return players;
    }

    public Level getRoomLevel() {
        return roomLevel;
    }

    public <T extends Room> T  getRoom(T room) {
        return room;
    }
}