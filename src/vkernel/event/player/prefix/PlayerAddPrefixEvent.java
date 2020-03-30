package vkernel.event.player.prefix;

import cn.nukkit.event.Cancellable;

public class PlayerAddPrefixEvent extends PrefixEvent implements Cancellable {
    public PlayerAddPrefixEvent(String playerName, String prefixId, String prefix) {
        super(playerName, prefixId, prefix);
    }
}
