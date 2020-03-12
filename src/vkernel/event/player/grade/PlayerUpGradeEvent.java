package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerUpGradeEvent extends GradeEvent implements Cancellable {
    private int upGrade, newGrade;

    public PlayerUpGradeEvent(Player player, int upGrade, int newGrade) {
        super(player.getName());
        this.upGrade = upGrade;
        this.newGrade = newGrade;
    }

    public PlayerUpGradeEvent(String player, int upGrade, int newGrade) {
        super(player);
        this.upGrade = upGrade;
        this.newGrade = newGrade;
    }

    public final int getUpGrade() {
        return upGrade;
    }

    public final int getNewGrade() {
        return newGrade;
    }
}
