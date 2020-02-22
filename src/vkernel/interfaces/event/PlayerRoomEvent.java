package vkernel.interfaces.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import vkernel.interfaces.Room;

/**
 * Created by TPride on 2020/2/23.
 */
public class PlayerRoomEvent extends PlayerEvent {
    private final Room room;
    private static final HandlerList handlerList = new HandlerList();

    public PlayerRoomEvent(Player player, Room room) {
        this.player = player;
        this.room = room;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
