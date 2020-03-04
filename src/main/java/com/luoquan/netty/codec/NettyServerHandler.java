package com.luoquan.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * NettyServerHandler
 * 1.我们自定义一个handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 2.这时我们自定义的Handler,才能称为一个handler
 *
 * @author LuoQuan
 * @date 2020/3/1 11:16
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    /**
     * 读取数据事件(这里我们可以读取客户端发送的消息)
     * 1.ChannelHandlerContext ctx:上下文对象,含有 管道pipeline,通道channel,地址
     * 2.Object msg:就是客户端发送的数据.默认是Object
     *
     * @param ctx 上下文
     * @param msg 客户端发送数据
     * @throws Exception
     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        //读取从客户端发送的StudentPOJO.Student
//        StudentPOJO.Student student = (StudentPOJO.Student) msg;
//
//        System.out.println("客户端发送的数据 id=" + student.getId() + ",名字=" + student.getName());
//    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush是write + flush
        //将数据写入到缓冲,并刷新
        //一般讲,我们对这个发送的数据进行编码
        String msg = "hello客户端,服务器端已经处理完毕";
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    /**
     * 处理异常,一般来说发生了异常,需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("客户端发送的数据 id=" + msg.getId() + ",名字=" + msg.getName());
    }
}
