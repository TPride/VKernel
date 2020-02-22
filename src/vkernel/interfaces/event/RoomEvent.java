package vkernel.interfaces.event;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import vkernel.interfaces.Room;

/**
 * Created by TPride on 2020/2/23.
 */
public class RoomEvent extends Event {
    protected Room room;
    private static final HandlerList handlerList = new HandlerList();

    public RoomEvent(Room room) {
        this.room = room;
    }

    public final Room getRoom() {
        return room;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
