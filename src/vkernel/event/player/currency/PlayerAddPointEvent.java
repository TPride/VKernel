package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;

public class PlayerAddPointEvent extends CurrencyEvent implements Cancellable {
    private int point;

    public PlayerAddPointEvent(String playerName, int point) {
        super(playerName);
        this.point = point;
    }

    public PlayerAddPointEvent(Player player, int point) {
        super(player.getName());
        this.point = point;
    }

    public final int getAddPoint() {
        return point;
    }

    public final int getNewPoint() {
        return PlayerData.getCurrency(playerName).getPoint() + point;
    }

    public final PlayerAddPointEvent setAddPoint(int point) {
        if (point >= 0)
            this.point = point;
        return this;
    }
}
