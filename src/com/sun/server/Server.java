package com.sun.server;

import com.sun.domain.ResultResponse;
import com.sun.server.controller.UserController;
import com.sun.server.dispatcher.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

public class Server implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        ServerSendAndReceive serverSendAndReceive = new ServerSendAndReceive(socket);
        while (true) {
            try {
                ResultResponse receive = serverSendAndReceive.receive();
                ResultResponse response = Dispatcher.dispatcher(receive, serverSendAndReceive);
                serverSendAndReceive.send(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                if (e instanceof SocketException) {
                    UserController.logout(serverSendAndReceive.getUser(), serverSendAndReceive);
                    System.out.println("下线了");
                }
                break;
            }
        }
    }

    public Server(Socket socket) throws IOException {
        this.socket = socket;
    }
}

class ServerImpl {

    private static final ExecutorService pool = new ThreadPoolExecutor(300,
            500, 6,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()); //线程池

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9898);
            while (true) {
                Socket accept = serverSocket.accept();
                System.out.println(accept + "  上线了！");
                pool.execute(new Server(accept));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
