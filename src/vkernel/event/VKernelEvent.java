package vkernel.event;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

/**
 * Created by TPride on 2020/2/28.
 */
public class VKernelEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    public VKernelEvent() {

    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
