package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.VKernel;
import vkernel.api.player.PlayerData;
import vkernel.event.VKernelPlayerEvent;

public class PlayerDownGradeEvent extends GradeEvent implements Cancellable {
    private int downGrade, oldGrade, newGrade;
    private final boolean canSetCancelled;

    public PlayerDownGradeEvent(String player, int downGrade, int oldGrade, int newGrade) {
        super(player);
        this.downGrade = downGrade;
        this.newGrade = newGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = true;
    }

    public PlayerDownGradeEvent(String player, int downGrade, int oldGrade, int newGrade, boolean canSetCancelled) {
        super(player);
        this.downGrade = downGrade;
        this.newGrade = newGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = canSetCancelled;
    }

    public PlayerDownGradeEvent(Player player, int downGrade, int oldGrade, int newGrade) {
        super(player.getName());
        this.downGrade = downGrade;
        this.newGrade = newGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = true;
    }

    public PlayerDownGradeEvent(Player player, int downGrade, int oldGrade, int newGrade, boolean canSetCancelled) {
        super(player.getName());
        this.downGrade = downGrade;
        this.newGrade = newGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = canSetCancelled;
    }

    public final int getDownGrade() {
        return downGrade;
    }

    public final int getOldGrade() {
        return oldGrade;
    }

    public final int getNewGrade() {
        return newGrade;
    }

    public final PlayerDownGradeEvent setDownGrade(int grade) {
        if (grade > 0 && PlayerData.getLevel(getPlayerName()).getGrade() >= grade) {
            downGrade = grade;
            newGrade = PlayerData.getLevel(getPlayerName()).getGrade() - downGrade;
        }
        return this;
    }

    @Override
    public void setCancelled() {
        if (canSetCancelled)
            super.setCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        if (canSetCancelled)
            super.setCancelled(cancelled);
    }
}
