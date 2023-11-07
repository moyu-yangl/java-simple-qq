package com.sun.server.controller;

import com.sun.domain.Message;
import com.sun.domain.ResultResponse;
import com.sun.server.ServerSendAndReceive;
import com.sun.server.StaticResource;
import com.sun.server.annotation.SystemAnnotation;
import com.sun.system.Constant;

import java.io.IOException;

import static com.sun.system.Constant.NEW_MESSAGE;

@SystemAnnotation
public class MessageController {

    @SystemAnnotation("message/send")
    public static ResultResponse sendMessage(Message message, ServerSendAndReceive serverSendAndReceive) {
        System.out.println(1);
        ResultResponse response = new ResultResponse(NEW_MESSAGE, message);
        ServerSendAndReceive sendAndReceive = StaticResource.sendAndReceiveMap.get(message.getReceiver());
        if (sendAndReceive == null) {
            return new ResultResponse<>(Constant.DEFAULT, "用户不在线或不存在");
        }
        try {
            sendAndReceive.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultResponse();
    }

}
