package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.api.player.classes.Currency;

public class PlayerAddPointEvent extends CurrencyEvent implements Cancellable {
    private int point;
    private String form;

    public PlayerAddPointEvent(String playerName, int point) {
        super(playerName);
        this.point = point;
        form = "CONSOLE";
    }

    public PlayerAddPointEvent(String playerName, int point, String form) {
        super(playerName);
        this.point = point;
        this.form = form;
    }

    public PlayerAddPointEvent(Player player, int point) {
        super(player.getName());
        this.point = point;
        form = "CONSOLE";
    }

    public PlayerAddPointEvent(Player player, int point, String form) {
        super(player.getName());
        this.point = point;
        this.form = form;
    }

    public final int getAddPoint() {
        return point;
    }

    public final int getNewPoint() {
        return new Currency(playerName).getPoint() + point;
    }

    public final PlayerAddPointEvent setAddPoint(int point) {
        if (point >= 0)
            this.point = point;
        return this;
    }

    public final String getForm() {
        return form;
    }
}
