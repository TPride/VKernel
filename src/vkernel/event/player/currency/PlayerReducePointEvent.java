package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;

public class PlayerReducePointEvent extends CurrencyEvent implements Cancellable {
    private int point;

    public PlayerReducePointEvent(String playerName, int point) {
        super(playerName);
        this.point = point;
    }

    public PlayerReducePointEvent(Player player, int point) {
        super(player.getName());
        this.point = point;
    }

    public final int getReducePoint() {
        return point;
    }

    public final int getNewPoint() {
        return PlayerData.getCurrency(playerName).getPoint() - point;
    }

    public final PlayerReducePointEvent setReducePoint(int point) {
        if (point >= 0 && PlayerData.getCurrency(playerName).getPoint() - point >= 0)
            this.point = point;
        return this;
    }
}
