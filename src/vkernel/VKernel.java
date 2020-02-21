package vkernel;

import cn.nukkit.plugin.PluginBase;

/**
 * Created by TPride on 2020/2/21.
 */
public class VKernel extends PluginBase {
    private static VKernel instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    public static VKernel getInstance() {
        return instance;
    }
}