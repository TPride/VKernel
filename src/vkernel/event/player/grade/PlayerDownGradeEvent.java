package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.api.player.datas.Grade;

public class PlayerDownGradeEvent extends GradeEvent implements Cancellable {
    private int downGrade, oldGrade, newGrade;
    private final boolean canSetCancelled;

    public PlayerDownGradeEvent(String player, int downGrade, int oldGrade) {
        super(player);
        this.downGrade = downGrade;
        this.newGrade = oldGrade - downGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = true;
    }

    public PlayerDownGradeEvent(String player, int downGrade, int oldGrade, boolean canSetCancelled) {
        super(player);
        this.downGrade = downGrade;
        this.newGrade = oldGrade - downGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = canSetCancelled;
    }

    public PlayerDownGradeEvent(Player player, int downGrade, int oldGrade) {
        super(player.getName());
        this.downGrade = downGrade;
        this.newGrade = oldGrade - downGrade;
        this.oldGrade = oldGrade;
        this.canSetCancelled = true;
    }

    public PlayerDownGradeEvent(Player player, int downGrade, int oldGrade, boolean canSetCancelled) {
        super(player.getName());
        this.downGrade = downGrade;
        this.newGrade = oldGrade - downGrade;
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
        if (grade > 0 && new Grade(getPlayerName()).getGrade() >= grade) {
            downGrade = grade;
            newGrade = new Grade(getPlayerName()).getGrade() - downGrade;
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
