package vkernel.api.player.datas;

import com.sun.istack.internal.NotNull;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.includes.ConfigKey;
import vkernel.includes.PlayerKey;
import java.io.File;
import java.util.Objects;
import java.util.Random;

public class Config { //配置文件
    private final String playerName;

    public Config(@NotNull String playerName) {
        this.playerName = playerName;
    }

    public final File getFile() {
        return new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[1], playerName + ".yml");
    }

    public final cn.nukkit.utils.Config getConfig() {
        return new cn.nukkit.utils.Config(getFile(), cn.nukkit.utils.Config.YAML);
    }

    public final boolean exists() {
        return playerName != null && Objects.requireNonNull(getFile()).exists();
    }

    public final boolean create() {
        if (exists() || playerName == null)
            return false;
        String numID = makeNumID();
        VKernel.getInstance().saveResource("initialPlayer.yml", VKernel.configDirs[1] + File.separator + playerName + ".yml", false);
        cn.nukkit.utils.Config config = getConfig();
        cn.nukkit.utils.Config config1 = VKernel.getInstance().getFileInstance().getConfig();
        config.set(PlayerKey.NUMID, numID);
        config.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY), config1.getInt(ConfigKey.CURRENCY.concat(ConfigKey.MONEY)));
        config.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.POINT), config1.getInt(ConfigKey.CURRENCY.concat(ConfigKey.POINT)));
        config.save();
        return true;
    }

    public final boolean delete() {
        if (!exists())
            return false;
        return getFile().delete();
    }

    private String makeNumID() {
        String numID = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++)
            numID += random.nextInt(10);
        if (PlayerData.existsNumID(numID))
            return makeNumID();
        return numID;
    }
}
