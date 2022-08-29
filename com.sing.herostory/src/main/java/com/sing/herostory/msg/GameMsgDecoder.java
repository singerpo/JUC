package com.sing.herostory.msg;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class GameMsgDecoder extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        if (!(msg instanceof BinaryWebSocketFrame)) {
            return;
        }
        // WebSocket二进制消息会通过HttpServerCodec解码成BinaryWebSocketFrame类对象
        BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = binaryWebSocketFrame.content();
        // 读取消息的长度
        byteBuf.readShort();
        // 读取消息的编号
        int msgCode = byteBuf.readShort();
        // 拿到真实的字节数组并打印
        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);
        GeneratedMessageV3 generatedMessageV3 = null;
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                generatedMessageV3 = GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
                break;
            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                generatedMessageV3 = GameMsgProtocol.WhoElseIsHereCmd.parseFrom(msgBody);
                break;
        }
        if(generatedMessageV3 != null){
            channelHandlerContext.fireChannelRead(generatedMessageV3);
        }

    }
}
