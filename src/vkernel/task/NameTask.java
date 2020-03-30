package vkernel.task;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import java.util.ArrayList;
import java.util.function.Consumer;

public class NameTask extends Task {
    public NameTask() {
        //no-code
    }

    @Override
    public void onRun(int i) {
        ArrayList<Player> players = new ArrayList<>(Server.getInstance().getOnlinePlayers().values());
        players.forEach(new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                if (player.isOnline()) {
                    PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(player);
                    player.setNameTag(TextFormat.RED + "NumID" + "[" + TextFormat.AQUA + playerData.getNumID() + TextFormat.RED + "]" + "\n" + PlayerData.getTagFormat(player));
                }
            }
        });
    }
}
