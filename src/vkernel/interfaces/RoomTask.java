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

    public int getWaitTime() {
        return wait;
    }

    public int getPlayTime() {
        return play;
    }

    public int getWishTime() {
        return wish;
    }

    public void init() {
        if (room.state != RoomState.NOT)
            return;
        wait = room.getRoomLevel().getWaitTime();
        play = room.getRoomLevel().getPlayTime();
        wish = room.getRoomLevel().getWishTime();
    }

    public void stop() {
        room.state = RoomState.NOT;
        VKernel.getInstance().getManager().getTaskManager().remove(room.getRoomLevel().levelName);
        room.players.clear();
        wait = room.getRoomLevel().getWaitTime();
        play = room.getRoomLevel().getPlayTime();
        wish = room.getRoomLevel().getWishTime();
        cancel();
    }
}