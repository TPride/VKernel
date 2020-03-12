package vkernel;

//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//               佛祖保佑         永无BUG

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import vkernel.command.HubCommand;
import vkernel.manager.Manager;
import java.io.File;

/**
 * Created by TPride on 2020/2/21.
 */
public class VKernel extends PluginBase {
    private static VKernel instance;
    private final FileInstance FileInstance = new FileInstance();;
    private final Manager manager = new Manager();
    public static final String[] configDirs = new String[] {
            "game",
            "user"
    };

    @Override
    public void onLoad() {
        instance = this;
        init();
        kernelInfo("加载完毕");
    }

    @Override
    public void onEnable() {
        initCommand();
        initEvent();
        kernelInfo("VKernel已启用");
    }

    @Override
    public void onDisable() {
        kernelInfo("VKernel已停用");
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
        initFile();
        initLevelRoom();
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

    public final FileInstance getFileInstance() {
        return FileInstance;
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

    /**
     * 初始化配置文件
     */
    private void initFile() {
        saveResource("setting.yml", false);
        saveResource("config.yml", false);
    }

    /**
     * 初始化指令
     */
    private void initCommand() {
        getServer().getCommandMap().register("hub", new HubCommand());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        Server.getInstance().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public class FileInstance {
        public FileInstance() {
        }

        public Config getSettings() {
            return new Config(getDataFolder() + File.separator + "setting.yml", Config.YAML);
        }

        public Config getConfig() {
            return new Config(getDataFolder() + File.separator + "config.yml", Config.YAML);
        }
    }
}