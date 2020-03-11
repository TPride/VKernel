package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.event.VKernelPlayerEvent;

public class PlayerAddExpEvent extends VKernelPlayerEvent implements Cancellable {
    private int addExp;
    private boolean isUpGrade;

    public PlayerAddExpEvent(Player player, int addExp, boolean isUpGrade) {
        super(player);
        this.addExp = addExp;
        this.isUpGrade = isUpGrade;
    }

    public final int getAddExp() {
        return addExp;
    }

    public final PlayerAddExpEvent setAddExp(int exp) {
        if (exp > 0) {
            PlayerData playerData = VKernel.getInstance().getManager().getPlayerManager().getPlayerData(player);
            addExp = exp;
            isUpGrade = playerData.level.getExp() + exp >= playerData.level.getUpLine();
        }
        return this;
    }

    public final boolean isUpGrade() {
        return isUpGrade;
    }
}
