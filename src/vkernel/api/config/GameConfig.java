package vkernel.api.config;

import cn.nukkit.utils.Config;
import com.sun.istack.internal.NotNull;
import vkernel.VKernel;
import java.io.File;

/**
 * Created by TPride on 2020/2/23.
 */
public class GameConfig {
    private String gameName;

    public GameConfig(String gameName) {
        this.gameName = gameName;
    }

    /**
     * 注册游戏文件夹
     * @return
     */
    public boolean registerFolder() {
        File dir = new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName);
        if (dir.exists())
            return false;
        return dir.mkdirs();
    }

    public String getDataFolder() {
        return getDataFolder() + File.separator + VKernel.configDirs[0] + gameName;
    }

    public boolean existsConfig(String levelName) {
        if (levelName == null || levelName.length() == 0)
            return false;
        if (!new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName).exists() || !new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName, levelName.concat(".yml")).exists())
            return false;
        return true;
    }

    public Config getConfig(String levelName) {
        if (!existsConfig(levelName))
            return null;
        return new Config(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName + File.separator + levelName.concat(".yml"), Config.YAML);
    }

    public Config createConfig(String levelName) {
        if (existsConfig(levelName))
            return getConfig(levelName);
        Config config = new Config(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName + File.separator + levelName.concat(".yml"), Config.YAML);
        config.save();
        return config;
    }

    public boolean deleteConfig(String levelName) {
        if (!existsConfig(levelName))
            return false;
        return new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[0] + File.separator + gameName, levelName.concat(".yml")).delete();
    }
}