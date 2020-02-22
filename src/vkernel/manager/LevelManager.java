package vkernel.manager;

import vkernel.VKernel;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by TPride on 2020/2/22.
 */
public class LevelManager {
    private LinkedHashMap<String, LinkedList<String>> levels = new LinkedHashMap<>();

    public LevelManager() {

    }

    public final boolean existsGame(String gameName) {
        if (gameName == null)
            return false;
        if (gameName.length() == 0)
            return false;
        return levels.containsKey(gameName);
    }

    public final boolean existsLevel(String gameName, String levelName) {
        if (gameName == null || levelName == null || gameName.length() == 0 || levelName.length() == 0)
            return false;
        if (!existsGame(gameName))
            return false;
        return levels.get(gameName).contains(levelName);
    }

    public final boolean existsLevel(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return false;
        for (Iterator<LinkedList<String>> iterator = levels.values().iterator(); iterator.hasNext();) {
            LinkedList<String> l = iterator.next();
            if (l.contains(levelName))
                return true;
        }
        return false;
    }

    public final boolean put(String gameName, String levelName) {
        if (gameName == null || levelName == null || gameName.length() == 0 || levelName.length() == 0)
            return false;
        if (!existsGame(gameName) || existsLevel(levelName))
            return false;
        if (!new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName, levelName.concat(".yml")).exists() || !new File(VKernel.getInstance().getServer().getDataPath() + File.separator + "worlds" + File.separator + levelName).exists())
            return false;
        levels.get(gameName).add(levelName);
        return true;
    }

    public final boolean remove(String gameName, String levelName) {
        if (gameName == null || levelName == null || gameName.length() == 0 || levelName.length() == 0)
            return false;
        if (!existsGame(levelName) || !existsLevel(levelName))
            return false;
        if (!existsLevel(gameName, levelName))
            return false;
        levels.get(gameName).remove(levelName);
        return true;
    }

    public final boolean remove(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return false;
        if (!existsLevel(levelName))
            return false;
        String gameName = getGameName(levelName);
        levels.get(gameName).remove(levelName);
        return true;
    }

    public final LinkedHashMap<String, LinkedList<String>> getLevels() {
        return levels;
    }

    public final LinkedList<String> getLevels(String gameName) {
        if (gameName == null || gameName.length() == 0)
            return new LinkedList<>();
        if (!existsGame(gameName))
            return new LinkedList<>();
        return levels.get(gameName);
    }

    public final LinkedList<String> getGame(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return new LinkedList<>();
        for (Iterator<LinkedList<String>> iterator = levels.values().iterator(); iterator.hasNext();) {
            LinkedList<String> l = iterator.next();
            if (l.contains(levelName))
                return l;
        }
        return new LinkedList<>();
    }

    public final String getGameName(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return null;
        if (!existsLevel(levelName))
            return null;
        int i = 0;
        for (Iterator<LinkedList<String>> iterator = levels.values().iterator(); iterator.hasNext();) {
            LinkedList<String> l = iterator.next();
            if (l.contains(levelName))
                break;
            i++;
        }
        return levels.keySet().toArray(new String[0])[i];
    }

    public final boolean registerGame(String gameName) {
        if (gameName == null || gameName.length() == 0)
            return false;
        if (existsGame(gameName))
            return false;
        if (!new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName).exists())
            return false;
        levels.put(gameName, new LinkedList<>());
        return true;
    }
}