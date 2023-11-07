package com.sun.client;

import com.sun.window.Login;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException, InvocationTargetException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9898);
        ClientSendAndReceive.setSocket(socket);
        SwingUtilities.invokeAndWait(() -> {
            Login login = new Login();
        });
    }
}
