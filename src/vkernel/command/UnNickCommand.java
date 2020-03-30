package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;

public class UnNickCommand extends Command {
    public UnNickCommand() {
        super("unnick", "卸下假名", "/unnick");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (commandSender instanceof Player) {
                PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName());
                if (playerData.nick.isUsingNick()) {
                    String nickName = playerData.nick.getNickName();
                    playerData.nick.unNick();
                    commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已卸下假名: " + TextFormat.RESET + nickName);
                } else
                    commandSender.sendMessage(TextFormat.WHITE + "Nick" + TextFormat.GRAY + " >> " + TextFormat.RED + "您还未佩戴假名");
            } else
                commandSender.sendMessage(TextFormat.RED + "请到游戏中输入");
        }
        return true;
    }
}
