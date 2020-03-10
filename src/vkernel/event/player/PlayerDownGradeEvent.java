package vkernel.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.event.VKernelPlayerEvent;

public class PlayerDownGradeEvent extends VKernelPlayerEvent implements Cancellable {
    private int downGrade, newGrade;

    public PlayerDownGradeEvent(Player player, int downGrade, int newGrade) {
        super(player);
        this.downGrade = downGrade;
        this.newGrade = newGrade;
    }

    public final int getDownGrade() {
        return downGrade;
    }

    public final int getNewGrade() {
        return newGrade;
    }

    public final PlayerDownGradeEvent setDownGrade(int grade) {
        if (grade > 0 && VKernel.getInstance().getManager().getPlayerManager().getPlayerData(player).level.getGrade() >= grade) {
            PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(player);
            downGrade = grade;
            newGrade = playerData.level.getGrade() - downGrade;
        }
        return this;
    }
}
