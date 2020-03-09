package vkernel.api.player;

import cn.nukkit.Player;
import vkernel.includes.PlayerState;
import vkernel.interfaces.Room;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerData {
    private final Player player;
    public final Game game;

    public PlayerData(Player player) {
        this.player = player;
        game = new Game();
    }

    public class File {

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
}