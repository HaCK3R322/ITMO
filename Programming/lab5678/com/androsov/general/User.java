package com.androsov.general;

import java.net.InetAddress;

public class User {
    public User(InetAddress userAddress) {
        this.userAddress = userAddress;
    }

    public User(InetAddress userAddress, String nickname, String password) {
        this.userAddress = userAddress;
        this.nickname = nickname;
        this.password = password;
    }

    final InetAddress userAddress;
    private String nickname;
    private String password;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(User.class))
            if (((User) obj).getNickname().equals(nickname)) return true;
        //if not equals
        return false;
    }
}
