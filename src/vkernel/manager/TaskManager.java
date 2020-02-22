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

    public final boolean exists(String levelName) {
        if (levelName == null)
            return false;
        return tasks.containsKey(levelName);
    }

    public final boolean put(String levelName, RoomTask roomTask) {
        if (levelName == null || levelName.length() == 0 || roomTask == null)
            return false;
        if (tasks.containsKey(levelName))
            return false;
        tasks.put(levelName, roomTask);
        return true;
    }

    public final boolean remove(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return false;
        if (!tasks.containsKey(levelName))
            return false;
        tasks.remove(levelName);
        return true;
    }

    public final RoomTask get(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return null;
        if (!tasks.containsKey(levelName))
            return null;
        return tasks.get(levelName);
    }
}