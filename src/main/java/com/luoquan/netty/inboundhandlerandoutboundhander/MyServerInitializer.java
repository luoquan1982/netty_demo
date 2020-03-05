package com.luoquan.netty.inboundhandlerandoutboundhander;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author luoquan
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //入栈的handler进行解码MyByteToLongDecoder
        //pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MybyteToLongDecoder2());

        pipeline.addLast(new MyLongToByteEncode());

        //自定义的handler处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
