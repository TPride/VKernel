package vkernel.interfaces;

import cn.nukkit.Server;
import com.sun.istack.internal.NotNull;

/**
 * Created by TPride on 2020/2/22.
 */
public abstract class Level {
    protected String levelName;
    protected String gameName;

    public Level(@NotNull String levelName) {
        this.levelName = levelName;
    }

    public void setGameName(@NotNull String gameName) {
        this.gameName = gameName;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getGameName() {
        return gameName;
    }

    /**
     * 等待时间
     * @return Integer
     */
    public abstract int getWaitTime();

    /**
     * 游戏时间
     * @return Integer
     */
    public abstract int getPlayTime();

    /**
     * 奖励时间
     * @return Integer
     */
    public abstract int getWishTime();

    public cn.nukkit.level.Level getLevel() {
        if (!Server.getInstance().isLevelLoaded(levelName))
            return null;
        return Server.getInstance().getLevelByName(levelName);
    }
}