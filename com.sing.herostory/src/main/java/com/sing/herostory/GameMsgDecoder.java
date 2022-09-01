package com.sing.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgDecoder extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMsgDecoder.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        if (!(msg instanceof BinaryWebSocketFrame)) {
            channelHandlerContext.fireChannelRead(((ByteBuf)msg).retain());
            return;
        }
        // WebSocket二进制消息会通过HttpServerCodec解码成BinaryWebSocketFrame类对象
        BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = binaryWebSocketFrame.content();
        // 读取消息的长度
        byteBuf.readShort();
        // 读取消息的编号
        int msgCode = byteBuf.readShort();
        Message.Builder msgBuilder = GameMsgRecognizer.getBuilderByMsgCode(msgCode);
        if(msgBuilder == null){
            LOGGER.error("无法解码的消息，msgCode={}",msgCode);
            return;
        }
        // 拿到消息体
        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);

        msgBuilder.clear();
        Message newMsg = msgBuilder.mergeFrom(msgBody).build();
        if (newMsg != null) {
            channelHandlerContext.fireChannelRead(newMsg);
        }

    }
}
