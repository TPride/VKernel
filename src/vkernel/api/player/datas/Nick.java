package vkernel.api.player.datas;

import vkernel.api.player.PlayerData;

public class Nick { //假名
    private String nickName = null;

    public Nick() {

    }

    public final boolean isUsingNick() {
        return nickName != null;
    }

    public Nick setNickName(String nickName) {
        if (nickName.length() == 0)
            return this;
        if (!PlayerData.existsNick(nickName))
            this.nickName = nickName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public Nick unNick() {
        nickName = null;
        return this;
    }
}
