package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.Server;
import vkernel.event.VKernelEvent;

public class CurrencyEvent extends VKernelEvent {
    protected String playerName;

    public CurrencyEvent(String playerName) {
        this.playerName = playerName;
    }

    public final String getPlayerName() {
        return playerName;
    }

    public final Player getPlayer() {
        return Server.getInstance().getPlayerExact(playerName);
    }
}
