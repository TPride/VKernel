package vkernel.manager;

/**
 * Created by TPride on 2020/2/22.
 */
public class Manager {
    private final LevelManager levelManager;
    private final PlayerManager playerManager;
    private final RoomManager roomManager;
    private final TaskManager taskManager;

    public Manager() {
        levelManager = new LevelManager();
        playerManager = new PlayerManager();
        roomManager = new RoomManager();
        taskManager = new TaskManager();
    }

    public final LevelManager getLevelManager() {
        return levelManager;
    }

    public final PlayerManager getPlayerManager() {
        return playerManager;
    }

    public final RoomManager getRoomManager() {
        return roomManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
