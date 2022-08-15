package com.sing.io.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author songbo
 * @since 2022-08-15
 */
public class PoolServer {
    ExecutorService pool = new ThreadPoolExecutor(20, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    private Selector selector;

    public static void main(String[] args) throws IOException {
        PoolServer poolServer = new PoolServer();
        poolServer.initServer(8686);
    }

    public void initServer(int port) throws IOException {
        // 全双工：读写可以同时进行
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8686));
        serverSocketChannel.configureBlocking(false);

        System.out.println("server started,listening on :" + serverSocketChannel.getLocalAddress());
        // "大管家"
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void listen() throws IOException {
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

    private void handle(SelectionKey selectionKey) {
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
            //移除
            selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_READ));
            pool.execute(new ThreadHandlerChannel(selectionKey));
        }
    }
}

class ThreadHandlerChannel extends Thread {
    SelectionKey selectionKey;

    public ThreadHandlerChannel(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public void run() {
        SocketChannel socketChannel = null;
        socketChannel = (SocketChannel) selectionKey.channel();
        // ByteBuffer的缺点：1.ByteBuffer的长度是固定的2.只有一个位置指针，进行读写状态切换时需要调用flip()或rewind()
        // Netty ByteBuf: 1.自动扩容2.通过两个位置指针来协助缓冲区的读写操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int size = 0;
            while ((size = socketChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                byteArrayOutputStream.write(byteBuffer.array(), 0, size);
                byteBuffer.clear();
            }
            byteArrayOutputStream.close();

            byte[] content = byteArrayOutputStream.toByteArray();
            ByteBuffer writeBuf = ByteBuffer.allocate(content.length);
            writeBuf.put(content);
            writeBuf.flip();
            socketChannel.write(writeBuf);
            if (size == -1) {
                socketChannel.close();
            } else {
                selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_READ);
                selectionKey.selector().wakeup();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}