package vkernel.api.player;

import cn.nukkit.Player;
import vkernel.VKernel;
import vkernel.api.player.classes.Config;
import vkernel.api.player.classes.Currency;
import vkernel.api.player.classes.Grade;
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
    public final Grade grade;
    public final Nick nick;
    public final Currency currency;

    public PlayerData(Player player) {
        this.player = player;
        nick = new Nick();
        game = new Game();
        config = new Config(player.getName());
        grade = new Grade(player.getName());
        currency = new Currency(player.getName());
    }

    public final Player getPlayer() {
        return player;
    }

    public final String getNumID() {
        return config.exists() ? config.getConfig().getString(PlayerKey.NUMID) : null;
    }

    public static final String getNumID(String playerName) {
        Config config = new Config(playerName);
        return config.exists() ? config.getConfig().getString(PlayerKey.NUMID) : null;
    }

    public static final String getPlayerNameByNumID(String numid) {
        File[] files = new File(VKernel.getInstance().getDataFolder() + "/" + VKernel.configDirs[1]).listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory())
                continue;
            if (!file.getName().contains(".") || !file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).equals("yml"))
                continue;
            String playerName = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (getNumID(playerName).equalsIgnoreCase(numid))
                return playerName;
        }
        return null;
    }

    public static final boolean existsNumID(String numid) {
        File[] files = new File(VKernel.getInstance().getDataFolder() + "/" + VKernel.configDirs[1]).listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory())
                continue;
            if (!file.getName().contains(".") || !file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).equals("yml"))
                continue;
            String playerName = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (getNumID(playerName).equalsIgnoreCase(numid))
                return true;
        }
        return false;
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

}