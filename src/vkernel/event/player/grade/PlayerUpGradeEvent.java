package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.api.player.classes.Grade;

public class PlayerUpGradeEvent extends GradeEvent implements Cancellable {
    private int upGrade, newGrade;

    public PlayerUpGradeEvent(Player player, int upGrade) {
        super(player.getName());
        this.upGrade = upGrade;
        this.newGrade = new Grade(player.getName()).getGrade() + upGrade;
    }

    public PlayerUpGradeEvent(String player, int upGrade) {
        super(player);
        this.upGrade = upGrade;
        this.newGrade = new Grade(player).getGrade() + upGrade;
    }

    public final int getUpGrade() {
        return upGrade;
    }

    public final int getNewGrade() {
        return newGrade;
    }
}
