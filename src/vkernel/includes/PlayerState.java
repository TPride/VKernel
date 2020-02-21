package vkernel.includes;

/**
 * Created by TPride on 2020/2/22.
 */
public enum PlayerState {

    UNPLAYING(0),
    PLAYING(1);

    private int state;
    private PlayerState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
