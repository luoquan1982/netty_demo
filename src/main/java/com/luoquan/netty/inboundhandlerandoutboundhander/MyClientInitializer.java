package com.luoquan.netty.inboundhandlerandoutboundhander;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author luoquan
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个出战的handler对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncode());

        //这是一个入站的解码器
        //pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MybyteToLongDecoder2());

        //加如一个自定义的handler,处理业务逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
