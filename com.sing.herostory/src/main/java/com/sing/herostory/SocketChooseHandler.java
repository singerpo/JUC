package com.sing.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.List;

/**
 * 协议初始化解码器
 * 用来判断实际使用什么协议
 * @author songbo
 * @since 2022-08-31
 */
public class SocketChooseHandler extends ByteToMessageDecoder {
    /*** 默认前缀最大长度为23*/
    private static final int MAX_LENGTH = 23;
    /*** Websocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        int length = byteBuf.readableBytes();
        if(length > MAX_LENGTH){
            length = MAX_LENGTH;
        }
        byteBuf.markReaderIndex();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        String prefix = new String(bytes);
        if(prefix.startsWith(WEBSOCKET_PREFIX)){
            // Http服务器编码解码器(将请求和应答消息解码为HTTP消息)
            channelHandlerContext.pipeline().addBefore("msgDecoder","http-codec",new HttpServerCodec())
                    // 内容长度限制(将HTTP消息的多个部分合成一条完整的HTTP消息)
                    .addBefore("msgDecoder","aggregator",new HttpObjectAggregator(65535))
                    // WebSocet协议处理器，在这里处理握手、ping等消息
                    .addBefore("msgDecoder","ProtocolHandler", new WebSocketServerProtocolHandler("/websocket"));
        }
        byteBuf.resetReaderIndex();
        channelHandlerContext.pipeline().remove(this.getClass());
    }
}
