package com.luoquan.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * NettyClient
 *
 * @author LuoQuan
 * @date 2020/3/1 12:42
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {

        //客户端需要一个时间循环组
        EventLoopGroup group = new NioEventLoopGroup();

        //创建一个客户端启动对象
        //注意客户端使用的不是ServerBootStrap而是BootStrap
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置相关参数
            bootstrap.group(group)             //设置线程组
                    .channel(NioSocketChannel.class)    //设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //在pipeline中加入ProtoBufEncode
                            ch.pipeline().addLast("encoder", new ProtobufEncoder())
                                    .addLast(new NettyClientHandler());    //加入自己的处理器
                        }
                    });

            System.out.println("客户端ok...");

            //启动客户端去连接服务器端
            //关于ChannelFuture要分析,涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
