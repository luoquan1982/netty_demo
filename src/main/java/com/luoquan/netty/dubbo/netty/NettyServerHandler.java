package com.luoquan.netty.dubbo.netty;

import com.luoquan.netty.dubbo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NettyServerHandler
 * 服务器这边的handler比较简单
 *
 * @author LuoQuan
 * @date 2020/3/9 10:22
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息,并调用我们的服务
        System.out.println("msg:" + msg);
        //客户端在调用服务器的api时,我们需要定义一个协议
        //比如我们要求每发一次消息时都必须以某个字符串开头 比如"HelloService#hello#***"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf('#') + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
