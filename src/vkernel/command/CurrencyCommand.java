package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.api.player.PlayerData;
import vkernel.api.player.classes.Currency;
import vkernel.includes.ConfigKey;

public class CurrencyCommand extends Command {
    public CurrencyCommand() {
        super("currency",  "货币", "/currency");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!VKernel.getInstance().isEnabled())
            return false;
        if (strings.length == 0) {
            Server.getInstance().dispatchCommand(commandSender, "currency help");
        } else if (strings.length >= 1) {
            switch (strings[0].toLowerCase()) {
                case "help":
                    String[] string = {
                            TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===",
                            TextFormat.RED + "[m = " + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "," + " p = " + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + "]",
                            TextFormat.GREEN + "/currency help - 货币帮助",
                            TextFormat.GREEN + "/currency my - 我的货币",
                            TextFormat.GREEN + "/currency give <m/p> <playerName> <IntValue> - 给指定用户转账" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + "或" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)),
                            TextFormat.GREEN + "/currency look <playerName> - 查询指定用户货币",
                            TextFormat.GREEN + "/currency <add/reduce/set> <m/p> <playerName> <IntValue> - 给指定用户(给予/扣除/设置)" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + "或" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)),
                            TextFormat.GREEN + "/currency name <m/p> <newName> - 设置货币名称"
                    };
                    for (int i = 0; i < string.length; i++) {
                        if (!commandSender.isOp() && (i == string.length - 3))
                            break;
                        commandSender.sendMessage(string[i]);
                    }
                    break;
                case "my":
                    if (commandSender instanceof Player) {
                        Currency currency = new Currency(commandSender.getName());
                        for (String i : new String[]{
                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===",
                                TextFormat.RED + "您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + currency.getMoney(),
                                TextFormat.RED + "您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + currency.getPoint()

                        })
                            commandSender.sendMessage(i);
                    } else commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                    break;
                case "give":
                    if (commandSender instanceof Player) {
                        Currency currency = new Currency(commandSender.getName());
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("m") && !strings[1].equalsIgnoreCase("p")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "改名失败,不存在该货币类型");
                                return true;
                            }
                            if (new vkernel.api.player.classes.Config(strings[2]).exists() && !strings[2].equals(commandSender.getName())) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    String playerName = StringMath.isIntegerNumber(strings[2]) ? PlayerData.getPlayerNameByNumID(strings[2]) : strings[2];
                                    Currency currency1 = new Currency(playerName);
                                    int mp = new Integer(strings[3]);
                                    switch (strings[1].toLowerCase()) {
                                        case "m":
                                            if (currency.getMoney() >= mp) {
                                               currency.reduceMoney(mp);
                                               currency1.addMoney(mp, commandSender.getName());
                                               commandSender.sendMessage(
                                                       TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===\n"
                                                       + TextFormat.GREEN + "转账成功!\n"
                                                       + TextFormat.YELLOW + "收账对象: " + TextFormat.AQUA + playerName + "\n"
                                                       + TextFormat.YELLOW + "数目: " + TextFormat.RED + mp + "\n"
                                                       + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET
                                               );
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "不足");
                                            break;
                                        case "p":
                                            if (currency.getPoint() >= mp) {
                                                currency.reducePoint(mp);
                                                currency1.addPoint(mp, commandSender.getName());
                                                commandSender.sendMessage(
                                                        TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===\n"
                                                                + TextFormat.GREEN + "转账成功!\n"
                                                                + TextFormat.YELLOW + "收账对象: " + TextFormat.AQUA + playerName + "\n"
                                                                + TextFormat.YELLOW + "数目: " + TextFormat.RED + mp + "\n"
                                                                + TextFormat.YELLOW + "货币类型: " + TextFormat.GOLD + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET
                                                );
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!您的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "不足");
                                            break;
                                        default:
                                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该类型的货币");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "转账失败!可能因为不存在该玩家或者该玩家是您自己");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                    break;
                case "look":
                    if (strings.length >= 2) {
                        String playerName = StringMath.isIntegerNumber(strings[1]) ? PlayerData.getPlayerNameByNumID(strings[1]) : strings[1];
                        if (!new vkernel.api.player.classes.Config(playerName).exists()) {
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该玩家");
                            return true;
                        }
                        Currency currency = new Currency(playerName);
                        for (String i : new String[]{
                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ===",
                                TextFormat.RED + playerName + "的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + currency.getMoney(),
                                TextFormat.RED + playerName + "的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + ": " + TextFormat.YELLOW + currency.getPoint()

                        })
                            commandSender.sendMessage(i);
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "请输入查询的玩家名称");
                    break;
                case "add":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("m") && !strings[1].equalsIgnoreCase("p")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该货币类型");
                                return true;
                            }
                            String playerName = StringMath.isIntegerNumber(strings[2]) ? PlayerData.getPlayerNameByNumID(strings[2]) : strings[2];
                            if (new vkernel.api.player.classes.Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    int mp = new Integer(strings[3]);
                                    Currency currency = new Currency(playerName);
                                    switch (strings[1].toLowerCase()) {
                                        case "m":
                                            currency.addMoney(mp, commandSender.getName());
                                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已给予玩家 " + TextFormat.AQUA + playerName + TextFormat.GREEN + " " + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET);
                                            break;
                                        case "p":
                                            currency.addPoint(mp, commandSender.getName());
                                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已给予玩家 " + TextFormat.AQUA + playerName + TextFormat.GREEN + " " + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET);
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "给予失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "给予失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
                case "reduce":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("m") && !strings[1].equalsIgnoreCase("p")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该货币类型");
                                return true;
                            }
                            String playerName = StringMath.isIntegerNumber(strings[2]) ? PlayerData.getPlayerNameByNumID(strings[2]) : strings[2];
                            if (new vkernel.api.player.classes.Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    int mp = new Integer(strings[3]);
                                    Currency currency = new Currency(playerName);
                                    switch (strings[1].toLowerCase()) {
                                        case "m":
                                            if (currency.getMoney() >= mp) {
                                                currency.reduceMoney(mp, commandSender.getName());
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已扣除玩家 " + TextFormat.AQUA + playerName + TextFormat.GREEN + " " + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET);
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "扣除失败!该玩家的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.RED + "不足以满足扣除的数值");
                                            break;
                                        case "p":
                                            if (currency.getPoint() >= mp) {
                                                currency.reducePoint(mp, commandSender.getName());
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已扣除玩家 " + TextFormat.AQUA + playerName + TextFormat.GREEN + " " + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET);
                                            } else
                                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "扣除失败!该玩家的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.RED + "不足以满足扣除的数值");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "扣除失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "扣除失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
                case "set":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("m") && !strings[1].equalsIgnoreCase("p")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该货币类型");
                                return true;
                            }
                            String playerName = StringMath.isIntegerNumber(strings[2]) ? PlayerData.getPlayerNameByNumID(strings[2]) : strings[2];
                            if (new vkernel.api.player.classes.Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) >= 0) {
                                    int mp = new Integer(strings[3]);
                                    Currency currency = new Currency(playerName);
                                    switch (strings[1].toLowerCase()) {
                                        case "m":
                                            currency.setMoney(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已设置玩家 " + TextFormat.AQUA + strings[2] + TextFormat.GREEN + " 拥有" + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET);
                                            Player player = Server.getInstance().getPlayerExact(playerName);
                                            if (player != null) {
                                                if (player.isOnline())
                                                    player.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "您拥有的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.MONEY_NAME)) + TextFormat.RESET + TextFormat.GREEN + "已被更改为" + TextFormat.RED + mp + TextFormat.GREEN + "个");
                                            }
                                            break;
                                        case "p":
                                            currency.setPoint(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已设置玩家 " + TextFormat.AQUA + strings[2] + TextFormat.GREEN + " 拥有" + TextFormat.RED + mp + TextFormat.GREEN + "个" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET);
                                            player = Server.getInstance().getPlayerExact(playerName);
                                            if (player != null) {
                                                if (player.isOnline())
                                                    player.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "您拥有的" + VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.CURRENCY.concat(ConfigKey.POINT_NAME)) + TextFormat.RESET + TextFormat.GREEN + "已被更改为" + TextFormat.RED + mp + TextFormat.GREEN + "个");
                                            }
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "设置失败!有效数字范围在n>=0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "设置失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
                case "name":
                    if (commandSender.isOp()) {
                        if (strings.length >= 3) {
                            if (!strings[1].equalsIgnoreCase("m") && !strings[1].equalsIgnoreCase("p")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "改名失败,不存在该货币类型");
                                return true;
                            }
                            Config config = VKernel.getInstance().getFileInstance().getConfig();
                            String after = config.getString(ConfigKey.CURRENCY.concat(strings[1].equalsIgnoreCase("m") ? ConfigKey.MONEY_NAME : ConfigKey.POINT_NAME));
                            config.set(ConfigKey.CURRENCY.concat(strings[1].equalsIgnoreCase("m") ? ConfigKey.MONEY_NAME : ConfigKey.POINT_NAME), strings[2]);
                            config.save();
                            commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已将" + TextFormat.YELLOW + after + TextFormat.RESET + TextFormat.GREEN + "改名为" + TextFormat.YELLOW + strings[2] + TextFormat.RESET);
                        } else
                            Server.getInstance().dispatchCommand(commandSender, "money help");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Currency" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
            }
        }
        return true;
    }
}
