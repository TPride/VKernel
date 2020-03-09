package vkernel.api;

import cn.nukkit.utils.Config;
import vkernel.VKernel;

import java.io.File;

public class FileApi {
    private final VKernel main;

    public FileApi(VKernel main) {
        this.main = main;
    }

    public Config getSettings() {
        return new Config(main.getDataFolder() + File.separator + "settings.yml", Config.YAML);
    }

    public Config getConfig() {
        return new Config(main.getDataFolder() + File.separator + "config.yml", Config.YAML);
    }
}