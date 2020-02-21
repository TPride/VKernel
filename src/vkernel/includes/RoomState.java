package vkernel.includes;

/**
 * Created by TPride on 2020/2/22.
 */
public enum RoomState {
    NOT(0),
    WAIT(1),
    PLAY(2),
    WISH(3);

    private int state;
    private RoomState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
