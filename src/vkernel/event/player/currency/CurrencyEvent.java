package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.Server;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
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
        Player player = Server.getInstance().getPlayerExact(playerName);
        if (player != null)
            return player;
        else {
            String name = VKernel.getInstance().getPlayer(playerName);
            if (name != null)
                return Server.getInstance().getPlayerExact(name);
            return null;
        }
    }
}
