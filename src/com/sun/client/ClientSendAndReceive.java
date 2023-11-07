package com.sun.client;

import com.sun.domain.ResultResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSendAndReceive {
    private static Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void setSocket(Socket socket) throws IOException {
        ClientSendAndReceive.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void send(ResultResponse response) {
        try {
            oos.writeObject(response);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultResponse receive() {
        ResultResponse rs;
        if (ois == null) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            rs = (ResultResponse) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
