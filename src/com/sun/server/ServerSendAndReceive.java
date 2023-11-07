package com.sun.server;

import com.sun.domain.ResultResponse;
import com.sun.domain.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSendAndReceive {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private User user;

    public ServerSendAndReceive(Socket socket) {
        this.socket = socket;
    }

    public void send(ResultResponse response) throws IOException {
        if (oos == null) {
            oos = new ObjectOutputStream(socket.getOutputStream());
        }
        oos.writeObject(response);
        oos.flush();

    }

    public ResultResponse receive() throws IOException, ClassNotFoundException {
        ResultResponse rs;
        if (ois == null) {
            ois = new ObjectInputStream(socket.getInputStream());
        }
        rs = (ResultResponse) ois.readObject();
        return rs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
