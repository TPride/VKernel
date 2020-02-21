package vkernel.interfaces;

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

    /**
     * 获取Level对象
     * @return cn.nukkit.level.Level
     */
    public abstract cn.nukkit.level.Level getLevel();
}