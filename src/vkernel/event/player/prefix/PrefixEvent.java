package vkernel.event.player.prefix;

import vkernel.event.VKernelEvent;

public class PrefixEvent extends VKernelEvent {
    protected String playerName;
    protected String prefixId;
    protected String prefix;

    public PrefixEvent(String playerName, String prefixId, String prefix) {
        this.playerName = playerName;
        this.prefixId = prefixId;
        this.prefix = prefix;
    }

    public final String getPlayerName() {
        return playerName;
    }

    public final String getPrefixId() {
        return prefixId;
    }

    public final String getPrefix() {
        return prefix;
    }
}
