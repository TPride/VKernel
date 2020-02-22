package vkernel.manager;

import cn.nukkit.Player;
import vkernel.includes.PlayerData;

import java.util.LinkedHashMap;

/**
 * Created by TPride on 2020/2/22.
 */
public class PlayerManager {
    private LinkedHashMap<String, PlayerData> players = new LinkedHashMap<>();

    public PlayerManager() {

    }

    public final boolean existsPlayer(String playerName) {
        if (playerName == null)
            throw new NullPointerException();
        else
            return players.containsKey(playerName);
    }

    public final boolean existsPlayer(Player player) {
        if (player == null)
            throw new NullPointerException();
        else
            return players.containsKey(player.getName());
    }

    public final PlayerData getPlayerData(String playerName) {
        if (playerName == null)
            return null;
        if (!existsPlayer(playerName))
            return null;
        return players.get(playerName);
    }

    public final PlayerData getPlayerData(Player player) {
        if (player == null)
            return null;
        if (!existsPlayer(player))
            return null;
        return players.get(player.getName());
    }

    public final boolean put(Player player, PlayerData playerData) {
        if (player == null || playerData == null)
            return false;
        if (!existsPlayer(player.getName())) {
            players.put(player.getName(), playerData);
            return true;
        }
        return false;
    }

    public final boolean remove(String playerName) {
        if (playerName == null)
            return false;
        if (existsPlayer(playerName)) {
            players.remove(playerName);
            return true;
        }
        return false;
    }

    public final LinkedHashMap<String, PlayerData> getPlayers() {
        return players;
    }
}
