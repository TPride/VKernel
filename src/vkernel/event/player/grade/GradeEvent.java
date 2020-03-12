package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.Server;
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
        return Server.getInstance().getPlayerExact(playerName);
    }
}
