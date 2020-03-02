package com.luoquan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author luoquan
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向管道加如处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加如一个netty提供的httpServerCodec codec=>[code - decoder]
        //HttpServerCodec说明
        //1.HttpServerCodec是netty提供的处理http的编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2.增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

        System.out.println("ok~~~");
    }
}
