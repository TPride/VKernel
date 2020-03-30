package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.includes.ConfigKey;

public class NickCommand extends Command {
    public NickCommand() {
        super("nick", "假名", "/nick <newName>");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (commandSender instanceof Player) {
                if (commandSender.isOp()) {
                    if (VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.NICKSWITCH))) {
                        if (strings.length >= 1) {
                            if (!PlayerData.existsNick(strings[0]))
                                commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为使用假名: " + TextFormat.AQUA + TextFormat.BOLD + VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).nick.setNickName(strings[0]).getNickName() + TextFormat.RESET);
                            else
                                commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.RED + "不能重复名字");
                        } else {
                            String randNick = PlayerData.randNick();
                            VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).nick.setNickName(randNick);
                            commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为您自动生成假名: " + TextFormat.AQUA + TextFormat.BOLD + TextFormat.AQUA + TextFormat.BOLD + VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).nick.setNickName(randNick).getNickName() + TextFormat.RESET);
                        }
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.RED + "未开启假名系统");
                } else
                    commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.RED + "您没有权限");
            } else
                commandSender.sendMessage(TextFormat.RED + "请到游戏中输入");
        }
        return true;
    }
}
