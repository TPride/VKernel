package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.event.VKernelPlayerEvent;

public class PlayerReduceExpEvent extends GradeEvent implements Cancellable {
    private int reduceExp;

    public PlayerReduceExpEvent(Player player, int reduceExp) {
        super(player.getName());
        this.reduceExp = reduceExp;
    }

    public PlayerReduceExpEvent(String player, int reduceExp) {
        super(player);
        this.reduceExp = reduceExp;
    }

    public final int getReduceExp() {
        return reduceExp;
    }
}