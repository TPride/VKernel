package vkernel.command;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.includes.ConfigKey;

public class SwitchCommand extends Command {
    public SwitchCommand() {
        super("switch", "VKernel配置开关指令", "/switch <args...>");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (commandSender.isOp()) {
                if (strings.length >= 2) {
                    if (!strings[1].equalsIgnoreCase("t") && !strings[1].equalsIgnoreCase("f")) {
                        commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.RED + "开关只能是T(t) 或 F(f)");
                        return true;
                    }
                    boolean sw = strings[1].equalsIgnoreCase("t");
                    switch (strings[0]) {
                        case "tag":
                            Config config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.TAGSWITCH), sw);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "TagShow已设为 " + sw);
                            break;
                        case "chat":
                            config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.CHATSWITCH), sw);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "ChatShow已设为 " + sw);
                            break;
                        case "nick":
                            config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.NICKSWITCH), sw);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "Nick已设为 " + sw);
                            break;
                        case "pre":
                            config = VKernel.getInstance().getFileInstance().getConfig();
                            config.set(ConfigKey.PREFIX.concat(ConfigKey.PREFIXSWITCH), sw);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "Prefix已设为 " + sw);
                            break;
                        default:
                            Server.getInstance().dispatchCommand(commandSender, "switch");
                            break;
                    }
                } else
                    commandSender.sendMessage(
                            TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Switch" + TextFormat.AQUA + " ===\n"
                            + TextFormat.GREEN + "/switch <key> <t/f> - 开启或关闭功能\n"
                            + TextFormat.AQUA + "tag(头上的称号显示)\n"
                            + TextFormat.AQUA + "chat(聊天显示)\n"
                            + TextFormat.AQUA + "nick(假名功能)\n"
                            + TextFormat.AQUA + "pre(称号功能)"
                    );
            } else
                commandSender.sendMessage(TextFormat.WHITE + "Switch" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
        }
        return true;
    }
}
