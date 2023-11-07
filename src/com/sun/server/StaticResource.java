package com.sun.server;

import com.sun.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticResource {
    //    注册过的用户
    public static final List<User> registeredUsers = new ArrayList<>();
    //    在线用户
    public static final List<User> onlineUsers = new ArrayList<>();
    //    用户对应的socket
    public static final Map<String, ServerSendAndReceive> sendAndReceiveMap = new HashMap<>();

    static {
        registeredUsers.add(new User("11", "11", "11"));
        registeredUsers.add(new User("22", "22", "22"));
        registeredUsers.add(new User("33", "33", "33"));
    }
}
