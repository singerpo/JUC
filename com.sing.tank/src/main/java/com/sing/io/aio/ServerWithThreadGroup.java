package com.sing.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

/**
 * @author songbo
 * @since 2022-08-15
 */
public class ServerWithThreadGroup {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);

        AsynchronousServerSocketChannel socketChannel = AsynchronousServerSocketChannel.open(threadGroup).
                bind(new InetSocketAddress("127.0.0.1", 8686));
        socketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                socketChannel.accept(null, this);
                try {
                    System.out.println(client.getRemoteAddress());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            System.out.println(new String(attachment.array(), 0, result));
                            client.write(ByteBuffer.wrap("Hello Client".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });

        while (true) {
            Thread.sleep(1000);
        }

    }
}
