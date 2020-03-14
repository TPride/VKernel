package vkernel.api.player.classes;

import cn.nukkit.Server;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.event.player.grade.PlayerAddExpEvent;
import vkernel.event.player.grade.PlayerDownGradeEvent;
import vkernel.event.player.grade.PlayerReduceExpEvent;
import vkernel.event.player.grade.PlayerUpGradeEvent;
import vkernel.includes.ConfigKey;
import vkernel.includes.PlayerKey;

public class Grade { //等级
    private final Config config;
    private String playerName;

    public Grade(String playerName) {
        this.playerName = playerName;
        config = new Config(playerName);
    }

    public final String getPlayerName() {
        return playerName;
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
            cn.nukkit.utils.Config config1 = config.getConfig();
            config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.GRADE), grade);
            config1.save();
            if (getExp() > getUpLine())
                setExp(0);
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
                    Server.getInstance().getPluginManager().callEvent(event = new PlayerUpGradeEvent(playerName, up));
                    if (!event.isCancelled()) {
                        config1.set(PlayerKey.GRADE_SYSTEM.concat(PlayerKey.EXP), exp);
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
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddExpEvent(playerName, exp, (getExp() + exp) >= getUpLine()));
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
                Server.getInstance().getPluginManager().callEvent(reduceExpEvent = new PlayerReduceExpEvent(playerName, exp));
                if (reduceExpEvent.isCancelled())
                    return false;
                setExp(getExp() - reduceExpEvent.getReduceExp());
            } else {
                if (getAllExp() < exp)
                    return false;
                exp -= getExp();
                int g = 1;
                for (; exp > 0; g++)
                    exp -= getUpLine(getGrade() - g);
                PlayerDownGradeEvent downGradeEvent;
                Server.getInstance().getPluginManager().callEvent(downGradeEvent = new PlayerDownGradeEvent(playerName, g - 1, getGrade(), false));
                setGrade(downGradeEvent.getNewGrade());
                setExp(Math.abs(exp));
            }
            return true;
        }
        return false;
    }

    public boolean addGrade(int grade) {
        if (config.exists() && grade > 0) {
            if (getGrade() + grade > VKernel.getInstance().getFileInstance().getConfig().getInt(ConfigKey.LEVEL.concat(ConfigKey.MAX)))
                return false;
            PlayerUpGradeEvent event = new PlayerUpGradeEvent(playerName, grade);
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
                PlayerDownGradeEvent event = new PlayerDownGradeEvent(playerName, grade, getGrade());
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