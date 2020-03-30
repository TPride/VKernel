package vkernel.event.player.prefix;

import cn.nukkit.event.Cancellable;

public class PlayerRemovePrefixEvent extends PrefixEvent implements Cancellable {
    public PlayerRemovePrefixEvent(String playerName, String prefixId, String prefix) {
        super(playerName, prefixId, prefix);
    }
}
