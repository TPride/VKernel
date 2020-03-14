package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.api.player.classes.Currency;

public class PlayerReducePointEvent extends CurrencyEvent implements Cancellable {
    private int point;
    private String form;

    public PlayerReducePointEvent(String playerName, int point) {
        super(playerName);
        this.point = point;
        form = "CONSOLE";
    }

    public PlayerReducePointEvent(String playerName, int point, String form) {
        super(playerName);
        this.point = point;
        this.form = form;
    }

    public PlayerReducePointEvent(Player player, int point) {
        super(player.getName());
        this.point = point;
        form = "CONSOLE";
    }

    public PlayerReducePointEvent(Player player, int point, String form) {
        super(player.getName());
        this.point = point;
        this.form = form;
    }

    public final int getReducePoint() {
        return point;
    }

    public final int getNewPoint() {
        return new Currency(playerName).getPoint() - point;
    }

    public final PlayerReducePointEvent setReducePoint(int point) {
        if (point >= 0 && new Currency(playerName).getPoint() - point >= 0)
            this.point = point;
        return this;
    }

    public final String getForm() {
        return form;
    }
}
