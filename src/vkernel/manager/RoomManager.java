package vkernel.manager;

import vkernel.includes.RoomState;
import vkernel.interfaces.Room;
import java.util.LinkedHashMap;

/**
 * Created by TPride on 2020/2/22.
 */
public class RoomManager {
    private LinkedHashMap<String, Room> rooms = new LinkedHashMap<>();

    public RoomManager() {

    }

    public final boolean existsRoom(String roomName) {
        if (roomName == null)
            return false;
        return rooms.containsKey(roomName);
    }

    public final Room getRoom(String roomName) {
        if (roomName == null)
            return null;
        if (!existsRoom(roomName))
            return null;
        return rooms.get(roomName);
    }

    public final boolean put(String roomName, Room room) {
        if (roomName == null || room == null)
            return false;
        else if (!existsRoom(roomName)) {
            rooms.put(roomName, room);
            return true;
        }
        return false;
    }

    public final boolean remove(String roomName) {
        if (roomName == null)
            return false;
        if (existsRoom(roomName)) {
            if (rooms.get(roomName).state == RoomState.NOT) {
                rooms.remove(roomName);
                return true;
            }
            return false;
        }
        return false;
    }

    public final LinkedHashMap<String, Room> getRooms() {
        return rooms;
    }
}