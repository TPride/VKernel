package vkernel.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import vkernel.VKernel;
import vkernel.includes.ConfigKey;

public class MoneyCommand extends Command {
    public MoneyCommand() {
        super("money", VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME) + "指令(货币)"), "/money");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!VKernel.getInstance().isEnabled())
            return false;
        
        return true;
    }
}
