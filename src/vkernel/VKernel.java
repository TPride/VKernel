package vkernel;

import cn.nukkit.plugin.PluginBase;
import vkernel.manager.Manager;

import java.io.File;

/**
 * Created by TPride on 2020/2/21.
 */
public class VKernel extends PluginBase {
    private static VKernel instance;
    private final Manager manager = new Manager();
    public static final String[] configDirs = new String[]{
            "game"
    };

    @Override
    public void onLoad() {
        instance = this;
        init();
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
}