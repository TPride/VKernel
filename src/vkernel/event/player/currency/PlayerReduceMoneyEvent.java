package vkernel.event.player.currency;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;

public class PlayerReduceMoneyEvent extends CurrencyEvent implements Cancellable {
    private int money;

    public PlayerReduceMoneyEvent(String playerName, int money) {
        super(playerName);
        this.money = money;
    }

    public PlayerReduceMoneyEvent(Player player, int money) {
        super(player.getName());
        this.money = money;
    }

    public final int getReduceMoney() {
        return money;
    }

    public final int getNewMoney() {
        return VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName).currency.getMoney() - money;
    }

    public final PlayerReduceMoneyEvent setReduceMoney(int money) {
        if (money >= 0 && VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName).currency.getMoney() - money >= 0)
            this.money = money;
        return this;
    }
}
