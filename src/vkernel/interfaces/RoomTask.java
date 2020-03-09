package vkernel.interfaces;

import cn.nukkit.scheduler.Task;
import com.sun.istack.internal.NotNull;
import vkernel.VKernel;
import vkernel.includes.RoomState;

/**
 * Created by TPride on 2020/2/22.
 */
public abstract class RoomTask extends Task {
    private int wait, play, wish;
    private Room room;

    public RoomTask(@NotNull Room room) {
        this.room = room;
        wait = room.getRoomLevel().getWaitTime();
        play = room.getRoomLevel().getPlayTime();
        wish = room.getRoomLevel().getWishTime();
    }

    public final Room getRoom() {
        return room;
    }

    public final int getWaitTime() {
        return wait;
    }

    public final int getPlayTime() {
        return play;
    }

    public final int getWishTime() {
        return wish;
    }

    public final void init() {
        wait = room.getRoomLevel().getWaitTime();
        play = room.getRoomLevel().getPlayTime();
        wish = room.getRoomLevel().getWishTime();
    }
}