package vkernel;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import vkernel.api.player.PlayerData;

/**
 * Created by TPride on 2020/2/24.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onPreLogin(PlayerPreLoginEvent event) {
        Player player = event.getPlayer();
        if (VKernel.getInstance().getManager().getTaskManager().exists(player.getName()))
            return;
        PlayerData playerData = new PlayerData(player);
        playerData.config.create();
        VKernel.getInstance().getManager().getPlayerManager().put(player, playerData);
    }
}
