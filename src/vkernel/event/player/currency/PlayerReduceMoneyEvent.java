package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.api.player.datas.Currency;

public class PlayerReduceMoneyEvent extends CurrencyEvent implements Cancellable {
    private int money;
    private String form;

    public PlayerReduceMoneyEvent(String playerName, int money) {
        super(playerName);
        this.money = money;
        form = "CONSOLE";
    }

    public PlayerReduceMoneyEvent(String playerName, int money, String form) {
        super(playerName);
        this.money = money;
        this.form = form;
    }

    public PlayerReduceMoneyEvent(Player player, int money) {
        super(player.getName());
        this.money = money;
        form = "CONSOLE";
    }

    public PlayerReduceMoneyEvent(Player player, int money, String form) {
        super(player.getName());
        this.money = money;
        this.form = form;
    }

    public final int getReduceMoney() {
        return money;
    }

    public final int getNewMoney() {
        return new Currency(playerName).getMoney() - money;
    }

    public final PlayerReduceMoneyEvent setReduceMoney(int money) {
        if (money >= 0 && new Currency(playerName).getMoney() - money >= 0)
            this.money = money;
        return this;
    }

    public final String getForm() {
        return form;
    }
}
