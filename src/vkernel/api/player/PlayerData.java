package vkernel.api.player;

import cn.nukkit.Player;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.includes.ConfigKey;
import vkernel.includes.PlayerKey;
import vkernel.includes.PlayerState;
import vkernel.interfaces.Room;
import java.io.File;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerData {
    private final Player player;
    public final Game game;
    public final Config config;

    public PlayerData(Player player) {
        this.player = player;
        game = new Game();
        config = new Config();
    }

    public class Game {
        public Room playRoom;
        private PlayerState state = PlayerState.UNPLAYING;
        public final Room getPlayRoom() {
            return playRoom;
        }

        public final Game setPlayRoom(Room room) {
            playRoom = room;
            return this;
        }

        public final PlayerState getState() {
            return state;
        }

        public final Game play() {
            if (state == PlayerState.PLAYING)
                return this;
            if (playRoom == null)
                return this;
            state = PlayerState.PLAYING;
            return this;
        }

        public final Game unplay() {
            if (state == PlayerState.UNPLAYING)
                return this;
            state = PlayerState.UNPLAYING;
            return this;
        }
    }

    public class Config {
        public final File getFile() {
            return new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[1], player.getName() + ".yml");
        }

        public final cn.nukkit.utils.Config getConfig() {
            return new cn.nukkit.utils.Config(getFile(), cn.nukkit.utils.Config.YAML);
        }

        public final boolean exists() {
            return getFile().exists();
        }

        public final boolean create() {
            if (exists())
                return false;
            VKernel.getInstance().saveResource("initialPlayer.yml", VKernel.configDirs[1] + File.separator + player.getName() + ".yml", false);
            return true;
        }

        public final boolean delete() {
            if (!exists())
                return false;
            return getFile().delete();
        }
    }

    public class Level {
        public int getUpLine() {
            if (getGrade() >= 0)
                return (int)StringMath.eval(VKernel.getInstance().getFileApi().getConfig().getString(ConfigKey.LEVEL.concat(ConfigKey.FORMAT)).replace("g", "" + getGrade()));
            return -1;
        }

        public int getGrade() {
            if (!config.exists())
                return -1;
            return config.getConfig().getInt(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.GRADE));
        }

        public int getExp() {
            if (!config.exists())
                return -1;
            return config.getConfig().getInt(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP));
        }

        public Level setGrade(int grade) {
            if (!config.exists() || grade < 0)
                return this;
            int line = getUpLine();
            cn.nukkit.utils.Config config1 = config.getConfig();
            config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.GRADE), grade);
            if (getUpLine() < line)
                config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), 0);
            config1.save();
            return this;
        }
    }
}