package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.api.player.PlayerData;
import vkernel.api.player.classes.Currency;
import vkernel.includes.ConfigKey;

public class MoneyCommand extends Command {
    public MoneyCommand() {
        super("money", VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME) + "指令(货币)"), "/money");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!VKernel.getInstance().isEnabled())
            return false;
        if (strings.length == 0) {
            Server.getInstance().dispatchCommand(commandSender, "money help");
        } else if (strings.length >= 1) {
            switch (strings[0].toLowerCase()) {
                case "help":
                    String[] string = {
                            TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===",
                            TextFormat.RED + "[m = " + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "," + " p = " + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + "]",
                            TextFormat.GREEN + "/money help - 货币帮助",
                            TextFormat.GREEN + "/money my - 我的货币",
                            TextFormat.GREEN + "/money give <m/p> <playerName> <IntValue> - 给某个用户转账" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + "或" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)),
                            TextFormat.GREEN + "/money <add/reduce/set> <m/p> <playerName> <IntValue> - 给某个用户(添加/减少/设置)" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + "或" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)),
                            TextFormat.GREEN + "/money name <m/p> <newName> - 设置货币名称"
                    };
                    for (int i = 0; i < string.length; i++) {
                        if (!commandSender.isOp() && (i == string.length - 3))
                            break;
                        commandSender.sendMessage(string[i]);
                    }
                    break;
                case "my":
                    if (commandSender instanceof Player) {
                        for (String i : new String[]{
                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + "===",
                                TextFormat.RED + "您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).currency.getMoney(),
                                TextFormat.RED + "您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).currency.getPoint()

                        })
                            commandSender.sendMessage(i);
                    } else commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                    break;
                case "give":
                    if (commandSender instanceof Player) {
                        Currency currency = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(commandSender.getName()).currency;
                        if (strings.length >= 4) {
                            if (PlayerData.getConfig(strings[2]).exists() && !strings[2].equals(commandSender.getName())) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    Currency currency1 = PlayerData.getCurrency(strings[2]);
                                    int mp = new Integer(strings[3]);
                                    switch (strings[1].toLowerCase()) {
                                        case "m":
                                            if (currency.getMoney() >= mp) {
                                               currency.reduceMoney(mp);
                                               currency1.addMoney(mp);
                                               commandSender.sendMessage(
                                                       TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + "===\n"
                                                       + TextFormat.GREEN + "转账成功!\n"
                                                       + TextFormat.YELLOW + "转账对象: " + TextFormat.AQUA + strings[2] + "\n"
                                                       + TextFormat.YELLOW + "数目: " + TextFormat.RED + mp + "\n"
                                                       + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET
                                               );
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "余额不足");
                                            break;
                                        case "p":
                                            if (currency.getPoint() >= mp) {
                                                currency.reducePoint(mp);
                                                currency1.addPoint(mp);
                                                commandSender.sendMessage(
                                                        TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + "===\n"
                                                                + TextFormat.GREEN + "转账成功!\n"
                                                                + TextFormat.YELLOW + "转账对象: " + TextFormat.AQUA + strings[2] + "\n"
                                                                + TextFormat.YELLOW + "数目: " + TextFormat.RED + mp + "\n"
                                                                + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET
                                                );
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "余额不足");
                                            break;
                                        default:
                                            Server.getInstance().dispatchCommand(commandSender, "money help");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!可能因为不存在该玩家或者该玩家是您自己");
                        }
                    } else commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                    break;
            }
        }
        return true;
    }
}
