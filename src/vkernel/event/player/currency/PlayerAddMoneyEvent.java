package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.api.player.PlayerData;

public class PlayerAddMoneyEvent extends CurrencyEvent implements Cancellable {
    private int money;
    private String form;

    public PlayerAddMoneyEvent(String playerName, int money) {
        super(playerName);
        this.money = money;
        form = "CONSOLE";
    }

    public PlayerAddMoneyEvent(String playerName, int money, String form) {
        super(playerName);
        this.money = money;
        this.form = form;
    }

    public PlayerAddMoneyEvent(Player player, int money) {
        super(player.getName());
        this.money = money;
        form = "CONSOLE";
    }

    public PlayerAddMoneyEvent(Player player, int money, String form) {
        super(player.getName());
        this.money = money;
        this.form = form;
    }

    public final int getAddMoney() {
        return money;
    }

    public final int getNewMoney() {
        return PlayerData.getCurrency(playerName).getMoney() + money;
    }

    public final PlayerAddMoneyEvent setAddMoney(int money) {
        if (money >= 0)
            this.money = money;
        return this;
    }

    public final String getForm() {
        return form;
    }
}
