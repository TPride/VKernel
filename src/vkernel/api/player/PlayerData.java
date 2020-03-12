package vkernel.api.player;

import cn.nukkit.Player;
import vkernel.api.player.classes.Config;
import vkernel.api.player.classes.Currency;
import vkernel.api.player.classes.Level;
import vkernel.includes.PlayerState;
import vkernel.interfaces.Room;

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
        config = new Config(player.getName());
        level = new Level(player.getName());
        currency = new Currency(player.getName());
    }

    public final Player getPlayer() {
        return player;
    }

    public static final Config getConfig(String playerName) {
        return new Config(playerName);
    }

    public static final Level getLevel(String playerName) {
        return new Level(playerName);
    }

    public static final Currency getCurrency(String playerName) {
        return new Currency(playerName);
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