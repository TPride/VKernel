package vkernel;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.utils.TextFormat;
import vkernel.api.StringMath;
import vkernel.api.player.PlayerData;
import vkernel.api.player.datas.Config;
import vkernel.api.player.datas.Prefix;
import vkernel.includes.ConfigKey;
import vkernel.task.NameTask;

/**
 * Created by TPride on 2020/2/24.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onPreLogin(PlayerPreLoginEvent event) {
        Player player = event.getPlayer();
        if (StringMath.isIntegerNumber(player.getName())) {
            player.kick(TextFormat.RED + "您不能用数字ID进入本服务器");
            return;
        }
        if (VKernel.getInstance().getManager().getTaskManager().exists(player.getName()))
            return;
        PlayerData playerData = new PlayerData(player);
        playerData.format = VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.FORMAT));
        playerData.chatFormat = VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.CHATFORMAT));
        VKernel.getInstance().getManager().getPlayerManager().put(player, playerData);
        new Config(player.getName()).create();
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.CHATSWITCH))) {
            PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(event.getPlayer());
            event.setFormat(playerData.chatFormat
                    .replace("@format", PlayerData.getTagFormat(event.getPlayer()) + TextFormat.RESET)
                    .replace("@grade", playerData.grade.getGrade() + "")
                    .replace("@msg", event.getMessage())
                    .replace("@name", VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.NICKSWITCH)) ? (playerData.nick.isUsingNick() ? playerData.nick.getNickName() : event.getPlayer().getName()) : event.getPlayer().getName()+ TextFormat.RESET)
                    .replace("@per", PlayerData.getPermissionString(event.getPlayer()) + TextFormat.RESET)
                    .replace("@pre", (VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.PREFIXSWITCH)) ? (playerData.prefix.isUsing() ? playerData.prefix.getUsingPrefix() : TextFormat.RED + "无") : TextFormat.RED + "未启用") + TextFormat.RESET)
            );
        }
    }
}