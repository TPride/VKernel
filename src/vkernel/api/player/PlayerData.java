package vkernel.api.player;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import com.sun.istack.internal.NotNull;
import vkernel.VKernel;
import vkernel.api.player.datas.*;
import vkernel.includes.ConfigKey;
import vkernel.includes.PlayerKey;
import vkernel.includes.PlayerState;
import vkernel.interfaces.Room;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerData {
    private final Player player;
    private final String name;
    public final Game game;
    public final Nick nick;
    public final Prefix prefix;
    public final Grade grade;
    public final Currency currency;
    public String format = "";
    public String chatFormat = "";

    public PlayerData(@NotNull Player player) {
        this.player = player;
        name = player.getName();
        nick = new Nick();
        game = new Game();
        prefix = new Prefix(player.getName());
        grade = new Grade(player.getName());
        currency = new Currency(player.getName());
    }

    public final Player getPlayer() {
        return player;
    }

    public final String getNumID() {
        Config config = new Config(player.getName());
        return config.exists() ? config.getConfig().getString(PlayerKey.NUMID) : null;
    }

    public final String getName() {
        return name;
    }

    public static String getNumID(String playerName) {
        Config config = new Config(playerName);
        return config.exists() ? config.getConfig().getString(PlayerKey.NUMID) : null;
    }

    public static String getPlayerNameByNumID(String numid) {
        File[] files = new File(VKernel.getInstance().getDataFolder() + "/" + VKernel.configDirs[1]).listFiles();
        if (files == null)
            return null;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            if (!file.getName().contains(".") || !file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).equals("yml"))
                continue;
            String playerName = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (numid.equalsIgnoreCase(getNumID(playerName)))
                return playerName;
        }
        return null;
    }

    public static boolean existsNumID(String numid) {
        File[] files = new File(VKernel.getInstance().getDataFolder() + "/" + VKernel.configDirs[1]).listFiles();
        if (files == null)
            return false;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            if (!file.getName().contains(".") || !file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).equals("yml"))
                continue;
            String playerName = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (numid.equalsIgnoreCase(getNumID(playerName)))
                return true;
        }
        return false;
    }

    public static boolean existsNick(String nickName) {
        if (nickName == null)
            return false;
        if (Server.getInstance().getPlayerExact(nickName) != null)
            return true;
        for (PlayerData playerData : new ArrayList<>(VKernel.getInstance().getManager().getPlayerManager().getPlayers().values())) {
            if (playerData.nick.isUsingNick())
                if (playerData.nick.getNickName().equals(nickName))
                    return true;
        }
        return false;
    }

    public static String getRealNameByNick(String nickName) {
        if (nickName == null)
            return null;
        for (PlayerData playerData : new ArrayList<>(VKernel.getInstance().getManager().getPlayerManager().getPlayers().values())) {
            if (playerData.nick.isUsingNick())
                if (playerData.nick.getNickName().equals(nickName))
                    return playerData.getName();
        }
        return null;
    }

    public static String randNick() {
        char[] engs = "ABCDEFGHIJKLMNOBQRSTUVWXYZ".toCharArray();
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            String c = new String(new char[]{engs[random.nextInt(engs.length)]});
            if (random.nextInt(2) == 1)
                c = c.toLowerCase();
            result += c;
        }
        return existsNick(result) ? randNick() : result;
    }

    public static String getTagFormat(Player player) {
        PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(player);
        return playerData.format
                .replace("@grade", playerData.grade.getGrade() + "")
                .replace("@name", VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.NICKSWITCH)) ? (playerData.nick.isUsingNick() ? playerData.nick.getNickName() : player.getName()) : player.getName()+ TextFormat.RESET)
                .replace("@per", getPermissionString(player) + TextFormat.RESET)
                .replace("@pre", (VKernel.getInstance().getFileInstance().getConfig().getBoolean(ConfigKey.PREFIX.concat(ConfigKey.PREFIXSWITCH)) ? (playerData.prefix.isUsing() ? playerData.prefix.getUsingPrefix() : TextFormat.RED + "无") : TextFormat.RED + "未启用") + TextFormat.RESET);
    }

    public static String getPermissionString(Player player) {
        return player.isOp() ? VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.OP)) : VKernel.getInstance().getFileInstance().getConfig().getString(ConfigKey.PREFIX.concat(ConfigKey.PLAYER));
    }

    public static void formatSend(String key) {
        if (!key.equalsIgnoreCase("tag") && !key.equalsIgnoreCase("chat"))
            return;
        cn.nukkit.utils.Config config = VKernel.getInstance().getFileInstance().getConfig();
        for (PlayerData playerData : new ArrayList<>(VKernel.getInstance().getManager().getPlayerManager().getPlayers().values())) {
            if (playerData.game.state == PlayerState.PLAYING)
                continue;
            switch (key) {
                case "tag":
                    playerData.format = config.getString(ConfigKey.PREFIX.concat(ConfigKey.FORMAT));
                    break;
                case "chat":
                    playerData.chatFormat = config.getString(ConfigKey.PREFIX.concat(ConfigKey.CHATFORMAT));
                    break;
                default:
                    break;
            }
        }
    }

    public static void formatSend(String key, boolean all) {
        if (!key.equalsIgnoreCase("tag") && !key.equalsIgnoreCase("chat"))
            return;
        cn.nukkit.utils.Config config = VKernel.getInstance().getFileInstance().getConfig();
        for (PlayerData playerData : new ArrayList<>(VKernel.getInstance().getManager().getPlayerManager().getPlayers().values())) {
            if (playerData.game.state == PlayerState.PLAYING && !all)
                continue;
            switch (key) {
                case "tag":
                    playerData.format = config.getString(ConfigKey.PREFIX.concat(ConfigKey.FORMAT));
                    break;
                case "chat":
                    playerData.chatFormat = config.getString(ConfigKey.PREFIX.concat(ConfigKey.CHATFORMAT));
                    break;
                default:
                    break;
            }
        }
    }

    public class Game { //游戏
        public Room playRoom;
        private PlayerState state = PlayerState.UNPLAYING;

        public Game() {

        }

        public final Room getPlayRoom() {
            return playRoom;
        }

        public final Game setPlayRoom(Room room) {
            playRoom = room;
            return this;
        }

        public final PlayerState getState() {
            return state;
        }

        public final Game play() {
            if (state == PlayerState.PLAYING)
                return this;
            if (playRoom == null)
                return this;
            state = PlayerState.PLAYING;
            return this;
        }

        public final Game unplay() {
            if (state == PlayerState.UNPLAYING)
                return this;
            state = PlayerState.UNPLAYING;
            return this;
        }
    }

}