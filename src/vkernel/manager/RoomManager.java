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

    public final boolean exists(String levelName) {
        if (levelName == null)
            return false;
        return rooms.containsKey(levelName);
    }

    public final Room getRoom(String levelName) {
        if (levelName == null)
            return null;
        if (!exists(levelName))
            return null;
        return rooms.get(levelName);
    }

    public final boolean put(String levelName, Room room) {
        if (levelName == null || room == null)
            return false;
        else if (!exists(levelName)) {
            rooms.put(levelName, room);
            return true;
        }
        return false;
    }

    public final boolean remove(String levelName) {
        if (levelName == null)
            return false;
        if (exists(levelName)) {
            if (rooms.get(levelName).state == RoomState.NOT) {
                rooms.remove(levelName);
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