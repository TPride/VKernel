package vkernel;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.TextFormat;
import vkernel.event.player.currency.PlayerAddMoneyEvent;
import vkernel.event.player.currency.PlayerAddPointEvent;
import vkernel.includes.ConfigKey;

public class CurrencyListener implements Listener {
    @EventHandler
    public void onPlayerAddMoney(PlayerAddMoneyEvent event) {
        if (!event.isCancelled()) {
            if (event.getPlayer() != null)
                event.getPlayer().sendMessage(TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===\n"
                        + TextFormat.GREEN + "你获得了一笔财富!\n"
                        + TextFormat.YELLOW + "发送方: " + TextFormat.AQUA + event.getForm() + "\n"
                        + TextFormat.YELLOW + "数目: " + TextFormat.RED + event.getAddMoney() + "\n"
                        + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET
                );
        }
    }

    @EventHandler
    public void onPlayerAddPoint(PlayerAddPointEvent event) {
        if (!event.isCancelled()) {
            if (event.getPlayer() != null)
                event.getPlayer().sendMessage(TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===\n"
                        + TextFormat.GREEN + "你获得了一笔财富!\n"
                        + TextFormat.YELLOW + "发送方: " + TextFormat.AQUA + event.getForm() + "\n"
                        + TextFormat.YELLOW + "数目: " + TextFormat.RED + event.getAddPoint() + "\n"
                        + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET
                );
        }
    }
}
