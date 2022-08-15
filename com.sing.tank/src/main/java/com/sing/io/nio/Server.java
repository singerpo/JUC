package com.sing.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author songbo
 * @since 2022-08-12
 */
public class Server {
    public static void main(String[] args) throws IOException {
        // 全双工：读写可以同时进行
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8686));
        serverSocketChannel.configureBlocking(false);

        System.out.println("server started,listening on :" + serverSocketChannel.getLocalAddress());
        // "大管家"
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 这里也是阻塞的，当有事件时返回
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                handle(selectionKey);
            }
        }
    }

    private static void handle(SelectionKey selectionKey) {
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                // String remoteIp = ((InetSocketAddress)socketChannel.getRemoteAddress()).getHostString();
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isReadable()) {
            SocketChannel socketChannel = null;
            socketChannel = (SocketChannel) selectionKey.channel();
            // ByteBuffer的缺点：1.ByteBuffer的长度是固定的2.只有一个位置指针，进行读写状态切换时需要调用flip()或rewind()
            // Netty ByteBuf: 1.自动扩容2.通过两个位置指针来协助缓冲区的读写操作
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            byteBuffer.clear();
            try {
                int len = socketChannel.read(byteBuffer);
                if (len != -1) {
                    System.out.println(new String(byteBuffer.array(), 0, len));
                }
                ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
                socketChannel.write(bufferToWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(socketChannel != null){
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
