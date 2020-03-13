package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;

public class PlayerAddMoneyEvent extends CurrencyEvent implements Cancellable {
    private int money;

    public PlayerAddMoneyEvent(String playerName, int money) {
        super(playerName);
        this.money = money;
    }

    public PlayerAddMoneyEvent(Player player, int money) {
        super(player.getName());
        this.money = money;
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
}
