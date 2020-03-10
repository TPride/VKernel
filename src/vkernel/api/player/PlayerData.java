package vkernel.api.player;

import cn.nukkit.Player;
import cn.nukkit.Server;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.event.player.PlayerAddExpEvent;
import vkernel.event.player.PlayerUpGradeEvent;
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
    public final Level level;

    public PlayerData(Player player) {
        this.player = player;
        game = new Game();
        config = new Config();
        level = new Level();
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
            cn.nukkit.utils.Config config = getConfig();
            cn.nukkit.utils.Config config1 = VKernel.getInstance().getFileInstance().getConfig();
            config.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY), config1.getInt(ConfigKey.CURRENCY.concat(ConfigKey.MONEY)));
            config.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.DIAMOND), config1.getInt(ConfigKey.CURRENCY.concat(ConfigKey.DIAMOND)));
            config.save();
            return true;
        }

        public final boolean delete() {
            if (!exists())
                return false;
            return getFile().delete();
        }
    }

    public class Level {
        public int getAllExp() {
            if (config.exists()) {

            }
            return -1;
        }

        public int getUpLine() {
            if (getGrade() >= 0)
                return (int)StringMath.eval(VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.LEVEL.concat(ConfigKey.FORMAT)).replace("g", "" + getGrade()));
            return -1;
        }

        public int getUpLine(int grade) {
            if (grade >= 0)
                return (int)StringMath.eval(VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.LEVEL.concat(ConfigKey.FORMAT)).replace("g", "" + grade));
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

        public boolean setGrade(int grade) {
            if (config.exists() && grade >= 0) {
                int line = getUpLine();
                cn.nukkit.utils.Config config1 = config.getConfig();
                config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.GRADE), grade);
                if (getUpLine() < line)
                    config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), 0);
                config1.save();
                return true;
            }
            return false;
        }

        public boolean setExp(int exp) {
            if (config.exists()) {
                int line = getUpLine();
                cn.nukkit.utils.Config config1 = config.getConfig();
                if (exp > 0) { //正数等级算法
                    if (line > exp) {
                        config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), exp);
                        config1.save();
                    } else {
                        int grade = getGrade();
                        if (grade + 1 >= VKernel.getInstance().getFileInstance().getConfig().getInt(ConfigKey.LEVEL.concat(ConfigKey.MAX)))
                            return false;
                        int up = 0;
                        while (exp >= line) {
                            if (grade + up >= VKernel.getInstance().getFileInstance().getConfig().getInt(ConfigKey.LEVEL.concat(ConfigKey.MAX)))
                                break;
                            exp -= line;
                            up++;
                            grade = grade + up;
                            line = getUpLine(grade);
                        }
                        PlayerUpGradeEvent event;
                        Server.getInstance().getPluginManager().callEvent(event = new PlayerUpGradeEvent(player, up, grade));
                        if (!event.isCancelled()) {
                            config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), event.getUpGrade());
                            config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.GRADE), event.getNewGrade());
                            config1.save();
                        } else return false;
                    }
                }
                return true;
            }
            return false;
        }

        public boolean addExp(int exp) {
            if (config.exists() && exp > 0) {
                PlayerAddExpEvent event;
                Server.getInstance().getPluginManager().callEvent(event = new PlayerAddExpEvent(player, exp, (getExp() + exp) >= getUpLine()));
                if (event.isCancelled())
                    return false;
                if (!setExp(getExp() + event.getAddExp())) {
                    event.setCancelled();
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}