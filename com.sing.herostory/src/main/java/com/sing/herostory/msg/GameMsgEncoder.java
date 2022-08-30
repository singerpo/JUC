package com.sing.herostory.msg;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg == null || !(msg instanceof GeneratedMessageV3)) {
            super.write(ctx, msg, promise);
            return;
        }
        int msgCode = -1;
        if(msg instanceof GameMsgProtocol.UserEntryResult){
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
        }else if(msg instanceof GameMsgProtocol.WhoElseIsHereResult){
            msgCode = GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE;
        }else if (msg instanceof GameMsgProtocol.UserMoveToResult){
            msgCode = GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE;
        }else if (msg instanceof GameMsgProtocol.UserQuitResult){
            msgCode = GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;
        } else{
            System.out.println("该消息未处理");
        }
        byte[] bytes = ((GeneratedMessageV3) msg).toByteArray();
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeShort(bytes.length);
        byteBuf.writeShort(msgCode);
        byteBuf.writeBytes(bytes);

        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(byteBuf);
        super.write(ctx,binaryWebSocketFrame,promise);
    }
}
