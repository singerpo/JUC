package com.sing.io.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @author songbo
 * @since 2022-08-12
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8686);
        socket.getOutputStream().write("HelloServer".getBytes());
        socket.getOutputStream().flush();

        System.out.println("write over,waiting for msg back...");
        byte[] bytes = new byte[1024];
        int len = socket.getInputStream().read(bytes);
        System.out.println(new String(bytes,0,len));
        socket.close();
    }
}
