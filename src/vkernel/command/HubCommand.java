package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.event.player.PlayerHubEvent;
import vkernel.includes.SettingKey;

/**
 * Created by TPride on 2020/2/28.
 */
public class HubCommand extends Command {
    public HubCommand() {
        super("hub", "回城", "/hub");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!VKernel.getInstance().isEnabled())
            return false;
        if (commandSender instanceof Player) {
            Config config = VKernel.getInstance().getFileInstance().getSettings();
            if (!config.exists(SettingKey.MAIN_WORLD))
                commandSender.sendMessage(TextFormat.RED + "在传送时出了点小问题");
            else {
                if (!Server.getInstance().isLevelLoaded(config.getString(SettingKey.MAIN_WORLD)))
                    commandSender.sendMessage(TextFormat.RED + "在传送时出现了点小问题");
                else {
                    try {
                        PlayerHubEvent event;
                        Server.getInstance().getPluginManager().callEvent(event = new PlayerHubEvent(((Player) commandSender).getPlayer()));
                        if (event.isCancelled()) {
                            commandSender.sendMessage(TextFormat.RED + "传送被取消");
                            return true;
                        }
                        ((Player) commandSender).getPlayer().teleport(Server.getInstance().getLevelByName(config.getString(SettingKey.MAIN_WORLD)).getSpawnLocation());
                    } catch (NullPointerException e) {
                        commandSender.sendMessage(TextFormat.RED + "在传送时出现了点小问题");
                        e.printStackTrace();
                    }
                }
            }
        } else commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
        return true;
    }
}
