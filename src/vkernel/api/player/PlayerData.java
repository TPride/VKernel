package vkernel.api.player;

import cn.nukkit.Player;
import vkernel.includes.PlayerState;
import vkernel.interfaces.Room;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerData {
    public Room playRoom;
    public final Player player;
    private PlayerState state = PlayerState.UNPLAYING;

    public PlayerData(Player player) {
        playRoom = null;
        this.player = player;
    }

    public final Room getPlayRoom() {
        return playRoom;
    }

    public final PlayerData setPlayRoom(Room room) {
        if (room == null)
            return this;
        playRoom = room;
        return this;
    }

    public final PlayerState getState() {
        return state;
    }

    public final PlayerData play() {
        if (state == PlayerState.PLAYING)
            return this;
        if (playRoom == null)
            return this;
        state = PlayerState.PLAYING;
        return this;
    }

    public final PlayerData unplay() {
        if (state == PlayerState.UNPLAYING)
            return this;
        state = PlayerState.UNPLAYING;
        return this;
    }
}