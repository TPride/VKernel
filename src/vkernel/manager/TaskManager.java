package vkernel.manager;

import vkernel.interfaces.RoomTask;
import java.util.LinkedHashMap;

/**
 * Created by TPride on 2020/2/22.
 */
public class TaskManager {
    private LinkedHashMap<String, RoomTask> tasks = new LinkedHashMap<>();

    public TaskManager() {

    }

    public final boolean existsTask(String roomName) {
        if (roomName == null)
            return false;
        return tasks.containsKey(roomName);
    }
}
