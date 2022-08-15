package com.sing.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author songbo
 * @since 2022-08-12
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8686));
        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(() -> {
                handle(socket);
            }).start();
        }
    }

    private static void handle(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            int len = socket.getInputStream().read(bytes);
            System.out.println(new String(bytes, 0, len));

            socket.getOutputStream().write(bytes,0,len);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
