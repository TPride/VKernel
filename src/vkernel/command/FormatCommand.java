package vkernel.command;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.includes.ConfigKey;

public class FormatCommand extends Command {
    public FormatCommand() {
        super("format", "VKernel配置文件格式指令", "/format <args...>");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (commandSender.isOp()) {
                if (strings.length >= 2) {
                    switch (strings[0]) {
                        case "level":
                            Config config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.LEVEL.concat(ConfigKey.FORMAT), strings[1]);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已将等级经验上限算式改为 " + TextFormat.RESET + TextFormat.AQUA + strings[1]);
                            break;
                        case "chat":
                            String re = "";
                            for (int i = 1; i < strings.length; i++)
                                re += strings[i] + (i + 1 < strings.length ? " " : "");
                            config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.CHATFORMAT), re);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已将聊天显示格式改为 " + TextFormat.RESET + TextFormat.AQUA + re);
                            break;
                        case "tag":
                            re = "";
                            for (int i = 1; i < strings.length; i++)
                                re += strings[i] + (i + 1 < strings.length ? " " : "");
                            config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.FORMAT), re);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已将名称显示格式改为 " + TextFormat.RESET + TextFormat.AQUA + re);
                            break;
                        case "send":
                            switch (strings[1]) {
                                case "tag":
                                    PlayerData.formatSend(strings[1]);
                                    commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已同步玩家的名称显示");
                                    break;
                                case "chat":
                                    PlayerData.formatSend(strings[1]);
                                    commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已同步玩家的聊天显示");
                                    break;
                                default:
                                    Server.getInstance().dispatchCommand(commandSender, "format");
                                    break;
                            }
                            break;
                        default:
                            Server.getInstance().dispatchCommand(commandSender, "format");
                            break;
                    }
                } else
                    commandSender.sendMessage(
                            TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Format" + TextFormat.AQUA + " ===\n"
                            + TextFormat.GREEN + "/format <level/chat/tag> <stringValue> - 更改等级算式或聊天显示格式或名称显示格式\n"
                            + TextFormat.GREEN + "/format send <tag/chat> - 同步玩家的聊天显示格式或名称显示格式\n"
                    );
            } else
                commandSender.sendMessage(TextFormat.WHITE + "Format" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
        }
        return true;
    }
}
