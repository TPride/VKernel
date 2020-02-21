package vkernel.includes;

import cn.nukkit.Player;
import vkernel.interfaces.Room;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerData {
    private Room playRoom;
    private Player player;
    private PlayerState state = PlayerState.UNPLAYING;

    public PlayerData(Player player) {
        playRoom = null;
        this.player = player;
    }

    public final Room getPlayRoom() {
        return playRoom;
    }

    public final boolean setPlayRoom(Room room) {
        if (room == null)
            throw new NullPointerException();
        if (room.state == RoomState.NOT || room.state == RoomState.WAIT) {
            if (state == PlayerState.UNPLAYING) {
                playRoom = room;
                return true;
            }
            return false;
        }
        return false;
    }

    public final PlayerState getState() {
        return state;
    }

    public final boolean play() {
        if (state == PlayerState.PLAYING)
            return false;
        if (playRoom == null)
            return false;
        state = PlayerState.PLAYING;
        return true;
    }

    public final boolean unplay() {
        if (state == PlayerState.UNPLAYING)
            return false;
        if (playRoom == null)
            return false;
        state = PlayerState.UNPLAYING;
        return true;
    }

    public final Player getPlayer() {
        return player;
    }
}