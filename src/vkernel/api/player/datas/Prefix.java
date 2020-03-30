package vkernel.api.player.datas;

import cn.nukkit.Server;
import com.sun.istack.internal.NotNull;
import vkernel.api.StringMath;
import vkernel.event.player.prefix.PlayerAddPrefixEvent;
import vkernel.event.player.prefix.PlayerRemovePrefixEvent;
import vkernel.includes.PlayerKey;
import java.util.*;

public class Prefix { //称号
    private final String playerName;
    private final Config config;

    public Prefix(@NotNull String playerName) {
        this.playerName = playerName;
        config = new Config(playerName);
    }

    public final String getPlayerName() {
        return playerName;
    }

    public final boolean existsId(String id) {
        return StringMath.isIntegerNumber(id) && getIds().contains(id);
    }

    public final boolean existsPrefix(String prefix) {
        return getPrefixs().contains(prefix);
    }

    public final boolean unUsing() {
        if (getUsingID() != null) {
            cn.nukkit.utils.Config config1 = config.getConfig();
            config1.set(PlayerKey.PREFIX.concat(PlayerKey.USING), null);
            config1.save();
            return true;
        }
        return false;
    }

    public final boolean setUsing(String id) {
        if (StringMath.isIntegerNumber(id)) {
            if (config.exists()) {
                cn.nukkit.utils.Config config1 = config.getConfig();
                config1.set(PlayerKey.PREFIX.concat(PlayerKey.USING), id);
                config1.save();
                return true;
            }
        }
        return false;
    }

    public final boolean isUsing() {
        return getUsingID() != null;
    }

    public final boolean isUsing(String id) {
        String usingID = getUsingID();
        return usingID != null && usingID.equalsIgnoreCase(id);
    }

    public final boolean isUsingPrefix(String prefix) {
        String usingPrefix = getUsingPrefix();
        return usingPrefix != null && usingPrefix.equals(prefix);
    }

    public final String getUsingID() {
        if (config.exists()) {
            String id = config.getConfig().getString(PlayerKey.PREFIX.concat(PlayerKey.USING));
            return id.equals("null") ? null : id;
        }
        return null;
    }

    public final String getUsingPrefix() {
        return getPrefix(getUsingID());
    }

    public final LinkedHashMap<String, String> getPrefixMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        if (config.exists())
            return config.getConfig().get(PlayerKey.PREFIX.concat(PlayerKey.PRELIST), result);
        return result;
    }

    public final LinkedList<String> getIds() {
        return new LinkedList<>(getPrefixMap().keySet());
    }

    public final LinkedList<String> getPrefixs() {
        return new LinkedList<>(getPrefixMap().values());
    }

    public final String getPrefix(String id) {
        if (!StringMath.isIntegerNumber(id))
            return null;
        return getPrefixMap().getOrDefault(id, null);
    }

    public final String getId(String prefix) {
        if (prefix == null)
            return null;
        LinkedHashMap<String, String> map = getPrefixMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!entry.getValue().equals(prefix))
                continue;
            return entry.getKey();
        }
        return null;
    }

    public final String addPrefix(String prefix) {
        return addPrefix(prefix, false);
    }

    public final String addPrefix(String prefix, boolean isUse) {
        if (!existsPrefix(prefix)) {
            PlayerAddPrefixEvent event;
            String id = makeID();
            Server.getInstance().getPluginManager().callEvent(event = new PlayerAddPrefixEvent(playerName, id, prefix));
            if (event.isCancelled())
                return null;
            cn.nukkit.utils.Config config1 = config.getConfig();
            config1.set(PlayerKey.PREFIX.concat(PlayerKey.PRELIST).concat(".".concat(id)), prefix);
            if (isUse)
                config1.set(PlayerKey.PREFIX.concat(PlayerKey.USING), id);
            config1.save();
            return id;
        }
        return null;
    }

    public final String removePrefix(String id) {
        if (existsId(id)) {
            PlayerRemovePrefixEvent event;
            Server.getInstance().getPluginManager().callEvent(event = new PlayerRemovePrefixEvent(playerName, id, getPrefix(id)));
            if (event.isCancelled())
                return null;
            if (isUsing(id))
                unUsing();
            cn.nukkit.utils.Config config1 = config.getConfig();
            LinkedHashMap<String, String> map = getPrefixMap();
            map.remove(id);
            config1.set(PlayerKey.PREFIX.concat(PlayerKey.PRELIST), map);
            config1.save();
            return id;
        }
        return null;
    }

    private String makeID() {
        String id = "";
        Random random = new Random();
        for (int i = 0; i <= 5; i++)
            id += random.nextInt(10);
        if (existsId(id))
            return makeID();
        return id;
    }
}
