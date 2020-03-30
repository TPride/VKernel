package vkernel.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.StringMath;
import vkernel.api.player.PlayerData;
import vkernel.api.player.datas.Config;
import vkernel.api.player.datas.Prefix;
import vkernel.includes.ConfigKey;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", "称号指令", "/prefix <args...>");
        setAliases(new String[]{"pre"});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (VKernel.getInstance().isEnabled()) {
            if (VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.PREFIXSWITCH))) {
                if (strings.length == 0)
                    Server.getInstance().dispatchCommand(commandSender, "prefix help");
                else {
                    switch (strings[0]) {
                        case "help":
                            String[] help = {
                                    TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Prefix" + TextFormat.AQUA + " ===",
                                    TextFormat.GREEN + "/prefix help - 查看帮助",
                                    TextFormat.GREEN + "/prefix using <playerName||nid> - 查看我或指定玩家正在使用的称号",
                                    TextFormat.GREEN + "/prefix use <id> - 使用指定的称号",
                                    TextFormat.GREEN + "/prefix un - 卸下称号",
                                    TextFormat.GREEN + "/prefix list - 查看自己的所有称号",
                                    TextFormat.GREEN + "/prefix add <playerName||nid> <newPrefix> - 给予玩家新称号",
                                    TextFormat.GREEN + "/prefix remove <playerName||nid> <id> - 删除玩家称号",
                            };
                            for (int i = 0; i < help.length; i++) {
                                if (!commandSender.isOp() && (i == help.length - 2))
                                    break;
                                commandSender.sendMessage(help[i]);
                            }
                            break;
                        case "using":
                            if (strings.length == 1) {
                                if (commandSender instanceof Player) {
                                    if (new Config(commandSender.getName()).exists()) {
                                        Prefix prefix = new Prefix(commandSender.getName());
                                        if (prefix.isUsing())
                                            commandSender.sendMessage(
                                                    TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Prefix" + TextFormat.AQUA + " ===\n"
                                                    + TextFormat.YELLOW + "您正在使用的称号: " + TextFormat.RESET + prefix.getUsingPrefix() + "\n"
                                                    + TextFormat.YELLOW + "称号ID: " + TextFormat.RED + TextFormat.BOLD + prefix.getUsingID()
                                            );
                                        else
                                            commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "您还没有佩戴称号");
                                    } else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到您的配置文件,获取失败");
                                } else
                                    commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                            } else {
                                String playerName = VKernel.getInstance().getPlayer(strings[1]);
                                if (playerName != null && new Config(playerName).exists()) {
                                    Prefix prefix = new Prefix(playerName);
                                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                    if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                                        playerName = playerData.nick.getNickName();
                                    if (prefix.isUsing())
                                        commandSender.sendMessage(
                                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Prefix" + TextFormat.AQUA + " ===\n"
                                                        + TextFormat.AQUA + playerName + TextFormat.RESET + TextFormat.YELLOW + "正在使用的称号: " + TextFormat.RESET + prefix.getUsingPrefix() + "\n"
                                                        + TextFormat.YELLOW + "称号ID: " + TextFormat.RED + TextFormat.BOLD + prefix.getUsingID()
                                        );
                                    else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.AQUA + playerName + TextFormat.RESET + TextFormat.RED + "还没有佩戴称号");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到该玩家的配置文件, 获取失败");
                            }
                            break;
                        case "use":
                            if (commandSender instanceof Player) {
                                if (strings.length > 1) {
                                    if (!new Config(commandSender.getName()).exists()) {
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到您的配置文件,使用失败");
                                        return true;
                                    }
                                    if (!StringMath.isIntegerNumber(strings[1])) {
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "称号ID必须是整数");
                                        return true;
                                    }
                                    Prefix prefix = new Prefix(commandSender.getName());
                                    if (prefix.existsId(strings[1])) {
                                        if (!strings[1].equalsIgnoreCase(prefix.getUsingID())) {
                                            prefix.setUsing(strings[1]);
                                            commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "成功使用称号 " + TextFormat.RESET + prefix.getUsingPrefix() + TextFormat.RESET);
                                        } else
                                            commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "您正在使用ID为" + TextFormat.AQUA + strings[1] + TextFormat.RESET + TextFormat.RED + "的称号");
                                    } else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "您没有ID为" + TextFormat.AQUA + strings[1] + TextFormat.RESET + TextFormat.RED + "的称号");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "请输入要使用的称号ID");
                            } else
                                commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                            break;
                        case "un":
                            if (commandSender instanceof Player) {
                                if (!new Config(commandSender.getName()).exists()) {
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到您的配置文件,卸下失败");
                                    return true;
                                }
                                Prefix prefix = new Prefix(commandSender.getName());
                                if (prefix.isUsing()) {
                                    prefix.unUsing();
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已卸下称号");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "您还没有佩戴称号");
                            } else
                                commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                            break;
                        case "list":
                            if (strings.length == 1) {
                                if (!new Config(commandSender.getName()).exists()) {
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到您的配置文件,卸下失败");
                                    return true;
                                }
                                LinkedHashMap<String, String> id_prefix = new Prefix(commandSender.getName()).getPrefixMap();
                                int i = 0;
                                String result = TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Prefix" + TextFormat.AQUA + " ===\n";
                                for (Iterator<Map.Entry<String, String>> iterator = id_prefix.entrySet().iterator(); iterator.hasNext(); i++) {
                                    Map.Entry<String, String> entry = iterator.next();
                                    result += TextFormat.RED + entry.getKey() + TextFormat.RESET + TextFormat.WHITE + " - " + TextFormat.RESET + entry.getValue() + TextFormat.RESET + ((i + 1 < id_prefix.size()) ? "\n" : "");
                                }
                                if (id_prefix.size() == 0)
                                    result = TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "您暂时还没有称号";
                                commandSender.sendMessage(result);
                            } else {
                                if (commandSender.isOp()) {
                                    String playerName = VKernel.getInstance().getPlayer(strings[1]);
                                    if (playerName != null && new Config(playerName).exists()) {
                                        PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                        LinkedHashMap<String, String> id_prefix = new Prefix(playerName).getPrefixMap();
                                        if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                                            playerName = playerData.nick.getNickName();
                                        int i = 0;
                                        String result = TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Prefix[" + TextFormat.WHITE + TextFormat.BOLD + playerName + TextFormat.RESET + TextFormat.YELLOW + "]" + TextFormat.AQUA + " ===\n";
                                        for (Iterator<Map.Entry<String, String>> iterator = id_prefix.entrySet().iterator(); iterator.hasNext(); i++) {
                                            Map.Entry<String, String> entry = iterator.next();
                                            result += TextFormat.RED + entry.getKey() + TextFormat.RESET + TextFormat.WHITE + " - " + TextFormat.RESET + entry.getValue() + TextFormat.RESET + ((i + 1 < id_prefix.size()) ? "\n" : "");
                                        }
                                        if (id_prefix.size() == 0)
                                            result = TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + playerName + "暂时还没有称号";
                                        commandSender.sendMessage(result);
                                    } else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到该玩家的配置文件, 获取失败");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                            }
                            break;
                        case "add":
                            if (commandSender.isOp()) {
                                String playerName = VKernel.getInstance().getPlayer(strings[1]);
                                if (playerName != null && new Config(playerName).exists()) {
                                    Prefix prefix = new Prefix(playerName);
                                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                    if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                                        playerName = playerData.nick.getNickName();
                                    if (!prefix.existsPrefix(strings[2])) {
                                        prefix.addPrefix(strings[2]);
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已给予" + playerName + "称号 " + TextFormat.RESET + strings[2]);
                                    } else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + playerName + "已拥有该称号");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到该玩家的配置文件, 给予失败");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                            break;
                        case "remove":
                            if (commandSender.isOp()) {
                                if (strings.length >= 3) {
                                    String playerName = VKernel.getInstance().getPlayer(strings[1]);
                                    if (playerName != null && new Config(playerName).exists()) {
                                        Prefix prefix = new Prefix(playerName);
                                        PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                        if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                                            playerName = playerData.nick.getNickName();
                                        if (prefix.existsId(strings[2]))
                                            commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已删除" + playerName + "称号 " + TextFormat.RESET + prefix.getPrefix(prefix.removePrefix(strings[2])));
                                        else
                                            commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + playerName + "没有该称号");
                                    } else
                                        commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "未找到该玩家的配置文件, 删除失败");
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "指令长度有误");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Prefix" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                            break;
                    }
                }
            } else
                commandSender.sendMessage(TextFormat.RED + "未开启称号系统");
        }
        return true;
    }
}
