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

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import vkernel.api.StringMath;
import vkernel.api.player.PlayerData;
import vkernel.command.*;
import vkernel.includes.ConfigKey;
import vkernel.manager.Manager;
import vkernel.task.NameTask;
import java.io.File;
import java.util.Iterator;

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
        //防止reload出现bug
        for (Iterator<Player> iterator = Server.getInstance().getOnlinePlayers().values().iterator(); iterator.hasNext();) {
            Player player = iterator.next();
            PlayerData playerData = new PlayerData(player);
            playerData.format = getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.FORMAT));
            playerData.chatFormat = getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.CHATFORMAT));
            VKernel.getInstance().getManager().getPlayerManager().put(player, playerData);
        }
        initTask();
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

    public final String getPlayer(String name) { //兼容假名
        if (name == null || name.length() == 0)
            return null;
        if (StringMath.isIntegerNumber(name) && name.length() == 5) { //如果是纯数字的字符串
            if (PlayerData.existsNumID(name)) //是否存在该nid
                return PlayerData.getPlayerNameByNumID(name);
        } else {
            String n = PlayerData.getRealNameByNick(name);
            if (n != null)
                return n;
            return name;
        }
        return null;
    }

    private void initLevelRoom() {
        File[] games = new File(getDataFolder() + File.separator + configDirs[0]).listFiles();
        String world = getServer().getDataPath() + File.separator + "worlds" + File.separator;
        if (games == null)
            return;
        for (File game : games) {
            manager.getLevelManager().registerGame(game.getName());
            File[] configs = game.listFiles();
            if (configs == null)
                continue;
            for (File config : configs) {
                String name = config.getName().substring(0, config.getName().lastIndexOf("."));
                if (!new File(world, name).exists())
                    continue;
                manager.getLevelManager().put(game.getName(), name);
                Server.getInstance().loadLevel(name);
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
        getServer().getCommandMap().register("VKernel", new HubCommand());
        getServer().getCommandMap().register("VKernel", new CurrencyCommand());
        getServer().getCommandMap().register("VKernel", new GradeCommand());
        getServer().getCommandMap().register("VKernel", new NumIDCommand());
        getServer().getCommandMap().register("VKernel", new NickCommand());
        getServer().getCommandMap().register("VKernel", new UnNickCommand());
        getServer().getCommandMap().register("VKernel", new PrefixCommand());
        getServer().getCommandMap().register("VKernel", new SwitchCommand());
        getServer().getCommandMap().register("VKernel", new FormatCommand());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        Server.getInstance().getPluginManager().registerEvents(new PlayerListener(), this);
        Server.getInstance().getPluginManager().registerEvents(new CurrencyListener(), this);
    }

    /**
     * 初始化计时器
     */
    private void initTask() {
        Server.getInstance().getScheduler().scheduleDelayedTask(this, ()->Server.getInstance().getScheduler().scheduleAsyncTask(this, new AsyncTask() {
            @Override
            public void onRun() {
                Server.getInstance().getScheduler().scheduleRepeatingTask(new NameTask(), 20);
            }
        }), 20);
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