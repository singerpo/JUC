package com.sing.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;

public interface ICmdHandler<TCmd extends GeneratedMessageV3>{
    void handle(ChannelHandlerContext channelHandlerContext, TCmd cmd);
}
