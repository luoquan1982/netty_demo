package com.luoquan.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NettyServer
 *
 * @author LuoQuan
 * @date 2020/3/1 10:39
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        // 创建BossGroup和WorkerGroup
        //说明
        //1.创建两个线程组bossGroup和workerGroup
        //2.bossGroup只是处理连接请求,真正的客户端业务处理会交给workGroup完成
        //3.两个都是无线循环
        //4.BossGroup和WorkerGroup含有的子线程(NioEventLoopGroup)的个数
        //默认是本机CPU核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象,去配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程的方式来进行设置
            bootstrap.group(bossGroup, workerGroup)                                 //设置两个线程组
                    .channel(NioServerSocketChannel.class)                         //使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)                  //设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)          //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道初始化对象(匿名对象的方式)
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //可以使用一个集合管理SocketChannel,在推送消息时,可以将业务加入到
                            // 各个channel对应的NIOEventLoop的taskQueue或者scheduleTaskQueue
                            System.out.println("客户socketChannel hashcode= " + ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());    //给管道添加一个处理器
                        }
                    });//给我们的workerGroup的EventLoop对应的管道设置处理器

            System.out.println("Server is ready...");

            //绑定一个端口并且同步,生成了一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //给channelFuture注册监听器,监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else{
                        System.out.println("监听端口失败");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
