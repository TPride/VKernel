package vkernel.api.player;

import cn.nukkit.Player;
import cn.nukkit.Server;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.event.player.currency.*;
import vkernel.event.player.grade.*;
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
    public final Nick nick;
    public final Currency currency;

    public PlayerData(Player player) {
        this.player = player;
        nick = new Nick();
        game = new Game();
        config = new Config();
        level = new Level();
        currency = new Currency();
    }

    public final Player getPlayer() {
        return player;
    }

    public class Nick { //假名
        public String nickName;

        public Nick() {
            nickName = null;
        }

        public boolean isUsingNick() {
            return !(nickName == null);
        }

        public Nick setNickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public String getNickName() {
            return nickName;
        }

        public Nick unNick() {
            nickName = null;
            return this;
        }
    }

    public class Game { //游戏
        public Room playRoom;
        private PlayerState state = PlayerState.UNPLAYING;

        public Game() {

        }

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

    public class Config { //配置文件
        private String playerName;

        public Config() {
            playerName = player.getName();
        }

        public Config(String playerName) {
            this.playerName = playerName;
        }

        public final File getFile() {
            return new File(VKernel.getInstance().getDataFolder() + File.separator + VKernel.configDirs[1], playerName + ".yml");
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
            VKernel.getInstance().saveResource("initialPlayer.yml", VKernel.configDirs[1] + File.separator + playerName + ".yml", false);
            cn.nukkit.utils.Config config = getConfig();
            cn.nukkit.utils.Config config1 = VKernel.getInstance().getFileInstance().getConfig();
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
    }

    public class Level { //等级
        public Level() {

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

        public int getAllExp() {
            if (!config.exists())
                return 0;
            int result = getExp();
            for (int a = getGrade() - 1; a >= 0; a--)
                result += getUpLine(a);
            return result;
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
                config1.save();
                if (getUpLine() < line) {
                    config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), 0);
                    config1.save();
                }
                return true;
            }
            return false;
        }

        public boolean setExp(int exp) {
            if (config.exists()) {
                int line = getUpLine();
                cn.nukkit.utils.Config config1 = config.getConfig();
                if (exp >= 0) { //正数等级算法
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
            if (config.exists() && exp >= 0) {
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

        public boolean reduceExp(int exp) {
            if (config.exists() && exp >= 0) {
                if (getExp() >= exp) {
                    PlayerReduceExpEvent reduceExpEvent;
                    Server.getInstance().getPluginManager().callEvent(reduceExpEvent = new PlayerReduceExpEvent(player, exp));
                    if (reduceExpEvent.isCancelled())
                        return false;
                    setExp(getExp() - reduceExpEvent.getReduceExp());
                } else {
                    if (getAllExp() < exp)
                        return false;
                    int g = 1;
                    int nowExp = getExp() + getUpLine(getGrade() - g);
                    for (; exp >= nowExp; g++)
                        nowExp += getUpLine(getGrade() - g);
                    PlayerReduceExpEvent reduceExpEvent;
                    Server.getInstance().getPluginManager().callEvent(reduceExpEvent = new PlayerReduceExpEvent(player, exp));
                    if (reduceExpEvent.isCancelled())
                        return false;
                    nowExp -= exp;
                    PlayerDownGradeEvent downGradeEvent = new PlayerDownGradeEvent(player, g, getGrade(), getGrade() - g, false);
                    Server.getInstance().getPluginManager().callEvent(downGradeEvent);
                    setGrade(downGradeEvent.getNewGrade());
                    setExp(nowExp);
                }
                return true;
            }
            return false;
        }

        public boolean addGrade(int grade) {
            if (config.exists() && grade > 0) {
                if (getGrade() + grade > VKernel.getInstance().getFileInstance().getConfig().getInt(ConfigKey.LEVEL.concat(ConfigKey.MAX)))
                    return false;
                PlayerUpGradeEvent event = new PlayerUpGradeEvent(player, grade, getGrade() + grade);
                Server.getInstance().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return false;
                setGrade(event.getNewGrade());
                return true;
            }
            return false;
        }

        public boolean reduceGrade(int grade) {
            if (config.exists() && grade > 0) {
                if (getGrade() - grade >= 0) {
                    PlayerDownGradeEvent event = new PlayerDownGradeEvent(player, grade, getGrade(), getGrade() - grade);
                    Server.getInstance().getPluginManager().callEvent(event);
                    if (event.isCancelled())
                        return false;
                    setGrade(event.getNewGrade());
                    setExp(0);
                    return true;
                }
            }
            return false;
        }
    }

    public class Currency { //货币
        public Currency() {

        }

        public int getMoney() {
            return config.exists() ? config.getConfig().getInt(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY)) : -1;
        }

        public int getPoint() {
            return config.exists() ? config.getConfig().getInt(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY)) : -1;
        }

        public boolean setMoney(int money) {
            if (config.exists() && money >= 0) {
                cn.nukkit.utils.Config config1 = config.getConfig();
                config1.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY), money);
                config1.save();
                return true;
            }
            return false;
        }

        public boolean setPoint(int point) {
            if (config.exists() && point >= 0) {
                cn.nukkit.utils.Config config1 = config.getConfig();
                config1.set(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.POINT), point);
                config1.save();
                return true;
            }
            return false;
        }

        public boolean addMoney(int money) {
            if (config.exists() && money >= 0) {
                PlayerAddMoneyEvent event = new PlayerAddMoneyEvent(player, money);
                Server.getInstance().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return false;
                setMoney(event.getNewMoney());
                return true;
            }
            return false;
        }

        public boolean addPoint(int point) {
            if (config.exists() && point >= 0) {
                PlayerAddPointEvent event = new PlayerAddPointEvent(player, point);
                Server.getInstance().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return false;
                setPoint(event.getNewPoint());
                return true;
            }
            return false;
        }

        public boolean reduceMoney(int money) {
            if (config.exists() && money >= 0) {
                PlayerReduceMoneyEvent event = new PlayerReduceMoneyEvent(player, money);
                Server.getInstance().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return false;
                setMoney(event.getNewMoney());
                return true;
            }
            return false;
        }

        public boolean reducePoint(int point) {
            if (config.exists() && point >= 0) {
                PlayerReducePointEvent event = new PlayerReducePointEvent(player, point);
                Server.getInstance().getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return false;
                setPoint(event.getNewPoint());
                return true;
            }
            return false;
        }
    }
}