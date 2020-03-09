package vkernel.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.event.VKernelPlayerEvent;

/**
 * Created by TPride on 2020/2/28.
 */
public class PlayerHubEvent extends VKernelPlayerEvent implements Cancellable {
    public PlayerHubEvent(Player player) {
        super(player);
    }
}
