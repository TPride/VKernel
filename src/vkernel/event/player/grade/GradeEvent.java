package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.Server;
import vkernel.VKernel;
import vkernel.event.VKernelEvent;

public class GradeEvent extends VKernelEvent {
    private String playerName;

    public GradeEvent(String playerName) {
        this.playerName = playerName;
    }

    public final String getPlayerName() {
        return playerName;
    }

    public final Player getPlayer() {
        Player player = Server.getInstance().getPlayerExact(playerName);
        if (player != null)
            return player;
        String name = VKernel.getInstance().getPlayer(playerName);
        if (name != null)
            return Server.getInstance().getPlayerExact(name);
        return null;
    }
}
