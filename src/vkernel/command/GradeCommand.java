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
import vkernel.api.player.datas.Grade;

public class GradeCommand extends Command {
    public GradeCommand() {
        super("grade", "等级", "/grade");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!VKernel.getInstance().isEnabled())
            return false;
        if (strings.length == 0) {
            Server.getInstance().dispatchCommand(commandSender, "grade help");
        } else {
            switch (strings[0]) {
                case "help":
                    String[] string = {
                            TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Grade" + TextFormat.AQUA + " ===",
                            TextFormat.GREEN + "/grade help - 查看帮助",
                            TextFormat.GREEN + "/grade my - 查看我的等级与经验",
                            TextFormat.GREEN + "/grade look <playerName||nid> - 查看指定用户的等级与经验",
                            TextFormat.GREEN + "/grade <add/set/reduce> <e/g> <playerName||nid> <IntValue> - (添加/设置/减去)指定玩家的(经验/等级)"
                    };
                    for (int i = 0; i < string.length; i++) {
                        if (!commandSender.isOp() && (i == string.length - 2))
                            break;
                        commandSender.sendMessage(string[i]);
                    }
                    break;
                case "my":
                    if (commandSender instanceof Player) {
                        if (new Config(commandSender.getName()).exists()) {
                            Grade grade = new Grade(commandSender.getName());
                            commandSender.sendMessage(TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Currency" + TextFormat.AQUA + " ==="
                                    + TextFormat.GREEN + "您的等级: " + TextFormat.RED + grade.getGrade() + "\n"
                                    + TextFormat.GREEN + "您的经验: " + TextFormat.YELLOW + grade.getExp() + "\n"
                                    + TextFormat.GREEN + "您的总经验: " + TextFormat.YELLOW + grade.getExp() + "\n"
                                    + TextFormat.AQUA + "您距离下一级升级还差 " + TextFormat.RED + (grade.getUpLine() - grade.getExp()) + TextFormat.AQUA + " 经验"
                            );
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该玩家");
                    } else
                        commandSender.sendMessage(TextFormat.RED + "请在游戏中输入");
                    break;
                case "look":
                    if (strings.length >= 2) {
                        String playerName = VKernel.getInstance().getPlayer(strings[1]);
                        if (playerName == null || !new Config(playerName).exists()) {
                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该玩家");
                            return true;
                        }
                        Grade grade = new Grade(playerName);
                        PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                        if (playerData != null && !playerName.equals(strings[1]) && playerData.nick.isUsingNick())
                            playerName = playerData.nick.getNickName();
                        for (String i : new String[]{
                                TextFormat.AQUA + "=== " + TextFormat.YELLOW + "Grade" + TextFormat.AQUA + " ===",
                                TextFormat.RED + playerName + "的等级: " + TextFormat.YELLOW + grade.getGrade(),
                                TextFormat.RED + playerName + "的经验: " + TextFormat.YELLOW + grade.getExp(),
                                TextFormat.RED + playerName + "的总经验: " + TextFormat.YELLOW + grade.getAllExp(),
                                TextFormat.AQUA + "TA距离下一级升级还差 " + TextFormat.RED + (grade.getUpLine() - grade.getExp()) + TextFormat.AQUA + " 经验"

                        })
                            commandSender.sendMessage(i);
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "请输入查询的玩家名称");
                    break;
                case "add":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("e") && !strings[1].equalsIgnoreCase("g")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该类型的选项");
                                return true;
                            }
                            String playerName = VKernel.getInstance().getPlayer(strings[2]);
                            if (playerName != null && new Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    int mp = new Integer(strings[3]);
                                    Grade grade = new Grade(playerName);
                                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                    if (playerData != null && !playerName.equals(strings[2]) && playerData.nick.isUsingNick())
                                        playerName = playerData.nick.getNickName();
                                    switch (strings[1].toLowerCase()) {
                                        case "g":
                                            grade.addGrade(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "添加 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 级");
                                            break;
                                        case "e":
                                            grade.addExp(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "添加 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 经验");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "添加失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "添加失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
                case "set":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("e") && !strings[1].equalsIgnoreCase("g")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该类型的选项");
                                return true;
                            }
                            String playerName = VKernel.getInstance().getPlayer(strings[2]);
                            if (playerName != null && new Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) >= 0) {
                                    int mp = new Integer(strings[3]);
                                    Grade grade = new Grade(playerName);
                                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                    if (playerData != null && !playerName.equals(strings[2]) && playerData.nick.isUsingNick())
                                        playerName = playerData.nick.getNickName();
                                    switch (strings[1].toLowerCase()) {
                                        case "g":
                                            grade.setGrade(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已设置玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "的等级为 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 级");
                                            break;
                                        case "e":
                                            grade.setExp(mp);
                                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已设置玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "的经验为 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 经验");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "设置失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "设置失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
                case "reduce":
                    if (commandSender.isOp()) {
                        if (strings.length >= 4) {
                            if (!strings[1].equalsIgnoreCase("e") && !strings[1].equalsIgnoreCase("g")) {
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "不存在该类型的选项");
                                return true;
                            }
                            String playerName = VKernel.getInstance().getPlayer(strings[2]);
                            if (playerName != null && new Config(playerName).exists()) {
                                if (StringMath.isIntegerNumber(strings[3]) && new Integer(strings[3]) > 0) {
                                    int mp = new Integer(strings[3]);
                                    Grade grade = new Grade(playerName);
                                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(playerName);
                                    if (playerData != null && !playerName.equals(strings[2]) && playerData.nick.isUsingNick())
                                        playerName = playerData.nick.getNickName();
                                    switch (strings[1].toLowerCase()) {
                                        case "g":
                                            if (grade.reduceGrade(mp))
                                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "减去 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 级");
                                            else
                                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "减去失败!该玩家等级不满足减去的要求");
                                            break;
                                        case "e":
                                            if (grade.reduceExp(mp))
                                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.GREEN + "已为玩家" + TextFormat.YELLOW + playerName + TextFormat.GREEN + "减去 " + TextFormat.AQUA + mp + TextFormat.GREEN + " 经验");
                                            else
                                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "减去失败!该玩家经验不满足减去的要求");
                                            break;
                                    }
                                } else
                                    commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "减去失败!有效数字范围在n>0以上");
                            } else
                                commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "减去失败!不存在该玩家");
                        } else
                            commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "请检查指令长度的正确性");
                    } else
                        commandSender.sendMessage(TextFormat.WHITE + "Grade" + TextFormat.GRAY + " >> " + TextFormat.RED + "你没有权限");
                    break;
            }
        }
        return true;
    }
}
