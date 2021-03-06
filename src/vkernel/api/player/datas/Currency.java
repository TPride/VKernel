package vkernel.api.player.datas;

import cn.nukkit.Server;
import com.sun.istack.internal.NotNull;
import vkernel.event.player.currency.PlayerAddMoneyEvent;
import vkernel.event.player.currency.PlayerAddPointEvent;
import vkernel.event.player.currency.PlayerReduceMoneyEvent;
import vkernel.event.player.currency.PlayerReducePointEvent;
import vkernel.includes.PlayerKey;

public class Currency { //货币
    private final String playerName;
    private final Config config;

    public Currency(@NotNull String playerName) {
        this.playerName = playerName;
        config = new Config(playerName);
    }

    public int getMoney() {
        return config.exists() ? config.getConfig().getInt(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.MONEY)) : -1;
    }

    public int getPoint() {
        return config.exists() ? config.getConfig().getInt(PlayerKey.CURRENCY_SYSTEM.concat(PlayerKey.POINT)) : -1;
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
            PlayerAddMoneyEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddMoneyEvent(playerName, money));
            if (event.isCancelled())
                return false;
            setMoney(event.getNewMoney());
            return true;
        }
        return false;
    }

    public boolean addMoney(int money, String form) {
        if (config.exists() && money >= 0) {
            PlayerAddMoneyEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddMoneyEvent(playerName, money, form));
            if (event.isCancelled())
                return false;
            setMoney(event.getNewMoney());
            return true;
        }
        return false;
    }

    public boolean addPoint(int point) {
        if (config.exists() && point >= 0) {
            PlayerAddPointEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddPointEvent(playerName, point));
            if (event.isCancelled())
                return false;
            setPoint(event.getNewPoint());
            return true;
        }
        return false;
    }

    public boolean addPoint(int point, String form) {
        if (config.exists() && point >= 0) {
            PlayerAddPointEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddPointEvent(playerName, point, form));
            if (event.isCancelled())
                return false;
            setPoint(event.getNewPoint());
            return true;
        }
        return false;
    }

    public boolean reduceMoney(int money) {
        if (config.exists() && money >= 0) {
            PlayerReduceMoneyEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerReduceMoneyEvent(playerName, money));
            if (event.isCancelled())
                return false;
            setMoney(event.getNewMoney());
            return true;
        }
        return false;
    }

    public boolean reduceMoney(int money, String form) {
        if (config.exists() && money >= 0) {
            PlayerReduceMoneyEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerReduceMoneyEvent(playerName, money, form));
            if (event.isCancelled())
                return false;
            setMoney(event.getNewMoney());
            return true;
        }
        return false;
    }

    public boolean reducePoint(int point) {
        if (config.exists() && point >= 0) {
            PlayerReducePointEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerReducePointEvent(playerName, point));
            if (event.isCancelled())
                return false;
            setPoint(event.getNewPoint());
            return true;
        }
        return false;
    }

    public boolean reducePoint(int point, String form) {
        if (config.exists() && point >= 0) {
            PlayerReducePointEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerReducePointEvent(playerName, point, form));
            if (event.isCancelled())
                return false;
            setPoint(event.getNewPoint());
            return true;
        }
        return false;
    }
}