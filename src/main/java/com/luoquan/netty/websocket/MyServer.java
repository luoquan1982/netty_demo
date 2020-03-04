package com.luoquan.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * MyServer
 *
 * @author LuoQuan
 * @date 2020/3/4 11:50
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //因为是基于http协议,使用http的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以快方式写的,添加chunkedWriterHandler
                            pipeline.addLast(new ChunkedWriteHandler());

                            /*
                             * 说明
                             * 1.http的数据在传输过程中是分段的,HttpObjectAggregator就是可以将多个段聚合起来
                             * 2.这就是为什么当浏览器发送大量数据时就会发出多次http请求的原因
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /*
                             * 说明
                             * 1.对于websocket,它的数据是以帧(frame)的形式传递
                             * 2.可以看到websocketFrame下面有六个子类
                             * 3.浏览器请求时 ws://localhost:7000/*** (请求的uri)
                             4.WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议,保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));

                            //自定义的handler,处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
