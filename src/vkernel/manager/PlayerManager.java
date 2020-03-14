package vkernel.manager;

import cn.nukkit.Player;
import vkernel.api.player.PlayerData;
import java.util.LinkedHashMap;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerManager {
    private LinkedHashMap<String, PlayerData> players = new LinkedHashMap<String, PlayerData>();

    public PlayerManager() {

    }

    public final boolean exists(String playerName) {
        if (playerName == null)
            return false;
        return players.containsKey(playerName);
    }

    public final boolean exists(Player player) {
        if (player == null)
            return false;
        return players.containsKey(player.getName());
    }

    public final PlayerData getPlayerData(String playerName) {
        if (playerName == null)
            return null;
        if (!exists(playerName))
            return null;
        return players.get(playerName);
    }

    public final PlayerData getPlayerData(Player player) {
        if (player == null)
            return null;
        if (!exists(player))
            return null;
        return players.get(player.getName());
    }

    public final boolean put(Player player, PlayerData playerData) {
        if (player == null || playerData == null)
            return false;
        if (!exists(player.getName())) {
            players.put(player.getName(), playerData);
            return true;
        }
        return false;
    }

    public final boolean remove(String playerName) {
        if (playerName == null)
            return false;
        if (exists(playerName)) {
            players.remove(playerName);
            return true;
        }
        return false;
    }

    public final LinkedHashMap<String, PlayerData> getPlayers() {
        return players;
    }
}
