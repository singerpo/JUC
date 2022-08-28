package com.sing.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * websocket消息协议
 * 前4个字节是消息头（前2个字节是消息长度，后2个字节是消息编号)
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) {
        System.out.println("收到客户端消息" + msg);
        BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = binaryWebSocketFrame.content();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("收到的字节=");
        for (byte aByte : bytes) {
            System.out.print(aByte);
            System.out.print(",");
        }

    }
}
