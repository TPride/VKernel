package vkernel;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import vkernel.manager.Manager;
import java.io.File;

/**
 * Created by TPride on 2020/2/21.
 */
public class VKernel extends PluginBase {
    private static VKernel instance;
    private final Manager manager = new Manager();
    public static final String[] configDirs = new String[] {
            "game",
            "user"
    };

    @Override
    public void onLoad() {
        instance = this;
        init();
        initLevelRoom();
        Server.getInstance().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void kernelInfo(String msg) {
        if (msg != null)
            getLogger().info("VKernel > ".concat(msg));
    }

    private void init() {
        File dir;
        for (String dirname : configDirs) {
            dir = new File(getDataFolder() + File.separator + dirname);
            if (!dir.exists())
                if (!dir.mkdirs())
                    kernelInfo("无法创建文件夹 ".concat(dirname));
        }
    }

    /**
     * VKernel Instance
     * @return VKernel
     */
    public static VKernel getInstance() {
        return instance;
    }

    /**
     * 获取管理机制
     * @return Manager
     */
    public final Manager getManager() {
        return manager;
    }

    private void initLevelRoom() {
        File[] games = new File(getDataFolder() + File.separator + configDirs[0]).listFiles();
        String world = getServer().getDataPath() + File.separator + "worlds" + File.separator;
        for (int i = 0; i < games.length; i++) {
            manager.getLevelManager().registerGame(games[i].getName());
            File[] configs = games[i].listFiles();
            for (int j = 0; j < configs.length; j++) {
                String name = configs[j].getName().substring(0, configs[j].getName().lastIndexOf("."));
                if (!new File(world, name).exists())
                    continue;
                manager.getLevelManager().put(games[i].getName(), name);
            }
        }
    }

    private void initFile() {
        saveResource("resource/setting.yml", false);
    }
}