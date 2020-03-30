package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.api.player.datas.Config;
import vkernel.includes.PlayerKey;

public class NumIDCommand extends Command {
    public NumIDCommand() {
        super("nid", "NumID指令", "/nid <args...>");
        setAliases(new String[]{"numid"});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (strings.length == 0) {
                if (commandSender instanceof Player) {
                    Config config = new Config(commandSender.getName());
                    if (config.exists())
                        commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "您的NumID是 " + TextFormat.AQUA + TextFormat.BOLD + config.getConfig().getString(PlayerKey.NUMID));
                    else
                        commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.RED + "无法获取您的NumID");
                } else
                    commandSender.sendMessage(TextFormat.RED + "控制台没有NumID");
            } else {
                switch (strings[0]) {
                    case "help":
                        String[] help = new String[] {
                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "NumID" + TextFormat.AQUA + " ===",
                                TextFormat.GREEN + "/nid - 查看自己的NumID",
                                TextFormat.GREEN + "/nid help - 查看帮助",
                                TextFormat.GREEN + "/nid look <playerName> - 查看指定玩家的NumID"
                        };
                        for (String s1 : help)
                            commandSender.sendMessage(s1);
                        break;
                    case "look":
                        if (strings.length >= 2) {
                            String playerName = VKernel.getInstance().getPlayer(strings[1]);
                            if (playerName == null) {
                                commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该玩家的配置文件");
                                return true;
                            }
                            Config config = new Config(playerName);
                            PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                            if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                                playerName = playerData.nick.getNickName();
                            if (config.exists())
                                commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.GREEN + playerName + "的NumID是 " + TextFormat.AQUA + TextFormat.BOLD + config.getConfig().getString(PlayerKey.NUMID));
                            else
                                commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该玩家的配置文件");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "NumID" + TextFormat.GRAY + " >> " + TextFormat.RED + "请输入要查询NumID的玩家名称");
                        break;
                    default:
                        VKernel.getInstance().getServer().dispatchCommand(commandSender, "nid help");
                        break;
                }
            }
        }
        return true;
    }
}
