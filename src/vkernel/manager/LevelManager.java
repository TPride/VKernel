package vkernel.manager;

import cn.nukkit.level.Level;

import java.util.LinkedList;

/**
 * Created by TPride on 2020/2/22.
 */
public class LevelManager {
    private LinkedList<String> gameLevels = new LinkedList<>();

    public LevelManager() {

    }

    public final boolean existsLevel(String levelName) {
        if (levelName == null)
            return false;
        return gameLevels.contains(levelName);
    }

    public final boolean existsLevel(Level level) {
        if (level == null)
            return false;
        return gameLevels.contains(level.getName());
    }

    public final boolean putLevel(String levelName) {
        if (levelName == null)
            return false;
        if (existsLevel(levelName))
            return false;
        gameLevels.add(levelName);
        return true;
    }

    public final boolean putLevel(Level level) {
        if (level == null)
            return false;
        if (existsLevel(level))
            return false;
        gameLevels.add(level.getName());
        return true;
    }

    public final boolean removeLevel(String levelName) {
        if (levelName == null)
            return false;
        if (!existsLevel(levelName))
            return false;
        gameLevels.remove(levelName);
        return true;
    }

    public final boolean removeLevel(Level level) {
        if (level == null)
            return false;
        if (!existsLevel(level))
            return false;
        gameLevels.remove(level.getName());
        return true;
    }

    public final LinkedList<String> getGameLevels() {
        return gameLevels;
    }
}