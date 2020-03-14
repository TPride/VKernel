package vkernel.event.player.grade;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import vkernel.api.player.classes.Grade;

public class PlayerAddExpEvent extends GradeEvent implements Cancellable {
    private int addExp;
    private boolean isUpGrade;

    public PlayerAddExpEvent(String player, int addExp, boolean isUpGrade) {
        super(player);
        this.addExp = addExp;
        this.isUpGrade = isUpGrade;
    }

    public PlayerAddExpEvent(Player player, int addExp, boolean isUpGrade) {
        super(player.getName());
        this.addExp = addExp;
        this.isUpGrade = isUpGrade;
    }

    public final int getAddExp() {
        return addExp;
    }

    public final PlayerAddExpEvent setAddExp(int exp) {
        if (exp > 0) {
            Grade grade = new Grade(getPlayerName());
            addExp = exp;
            isUpGrade = grade.getExp() + exp >= grade.getUpLine();
        }
        return this;
    }

    public final boolean isUpGrade() {
        return isUpGrade;
    }
}
