package com.sun.server.controller;

import com.sun.domain.ResultResponse;
import com.sun.domain.User;
import com.sun.server.ServerSendAndReceive;
import com.sun.server.annotation.SystemAnnotation;
import com.sun.system.Constant;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sun.server.StaticResource.*;

@SystemAnnotation
public class UserController {

    @SystemAnnotation("user/login")
    public static ResultResponse login(User user, ServerSendAndReceive serverSendAndReceive) {
        List<User> registered = registeredUsers.stream()
                .filter(u -> u.getId().equals(user.getId())).collect(Collectors.toList());
        if (registered.size() > 0) {
            for (User u : registered) {
                if (u.getId().equals(user.getId()) && u.getPassword().equals(user.getPassword())) {

                    onlineUsers.add(u);
                    sendAndReceiveMap.put(u.getId(), serverSendAndReceive);
                    sendToAll(u, Constant.USER_ONLINE);
                    try {
                        serverSendAndReceive.setUser(u);
                        serverSendAndReceive.send(new ResultResponse(u));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return new ResultResponse<>(Constant.ONLINE_USERS, onlineUsers);
                }
            }
            return new ResultResponse(Constant.DEFAULT, "账号或密码错误");
        }
        return new ResultResponse(Constant.REGISTER_NEED);
    }

    @SystemAnnotation("user/register")
    public static ResultResponse register(User user, ServerSendAndReceive serverSendAndReceive) {
        List<User> registered = registeredUsers.stream()
                .filter(u -> u.getId().equals(user.getId())).collect(Collectors.toList());
        if (registered.size() > 0) {
            return new ResultResponse(Constant.REGISTERED);
        }
        registeredUsers.add(user);
        return new ResultResponse();
    }

    @SystemAnnotation("user/logout")
    public static ResultResponse logout(User user, ServerSendAndReceive serverSendAndReceive) {
        for (int i = onlineUsers.size() - 1; i >= 0; i--) {
            if (onlineUsers.get(i).getId().equals(user.getId())) {
                onlineUsers.remove(i);
            }
        }
        sendAndReceiveMap.remove(user.getId());
        sendToAll(user, Constant.USER_LOGOUT);

        System.out.println(onlineUsers);
        System.out.println(sendAndReceiveMap);

        return new ResultResponse();
    }

    private static void sendToAll(User user, Constant constant) {
        ResultResponse response = new ResultResponse(constant, user);

        Map<String, ServerSendAndReceive> map = sendAndReceiveMap;
        for (String userId : sendAndReceiveMap.keySet()) {
            if (!userId.equals(user.getId())) {
                ServerSendAndReceive sendAndReceive = map.get(userId);
                try {
                    sendAndReceive.send(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
