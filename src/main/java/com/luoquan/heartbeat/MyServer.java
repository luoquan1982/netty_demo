package com.luoquan.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * MyServer
 *
 * @author LuoQuan
 * @date 2020/3/4 9:57
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {

        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))  //在bossGroup中增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入一个netty提供的IdleStateHandler
                            /**
                             * 说明
                             * 1.IdleStateHandler是netty的处理空闲状态的处理器
                             * 2.long readerIdleTime:表示多长时间没有读,就会发送一个心跳检测包检测是否还是连接的状态
                             * 3.long writerIdleTime:表示多长时间没有写,就会发送一个心跳检测包检测是否还是连接的状态
                             * 4.long allIdleTime:表示多长时间没有读写,就会发送一个心跳检测包检测是否还是连接的状态
                             * 5.Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             *  read, write, or both operation for a while.
                             *  6.当IdleStateEvent触发后,就会传递给管道的下一个handler去处理
                             *  通过调用(触发)下一个handler的userEventTriggered,在该方法中去处理IdleStateEvent(读空闲,写空闲,读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(13,5,7, TimeUnit.SECONDS));

                            //加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new MyServerHandler());

                        }
                    });

            //启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
