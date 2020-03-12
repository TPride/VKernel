package vkernel.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

/**
 * Created by TPride on 2020/2/28.
 */
public class VKernelPlayerEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    public VKernelPlayerEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
