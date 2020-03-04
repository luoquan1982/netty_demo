package com.luoquan.netty.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * NettyClientHandler
 *
 * @author LuoQuan
 * @date 2020/3/1 12:59
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时间就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override

    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //随机发送Student或者Worker对象
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;

        if (0 == random) {
            //发送一个学生
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setName("罗闰杰").setId(5).build())
                    .build();
        } else {
            //发送一个worker对象
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setName("罗全").setAge(38).build())
                    .build();
        }

        ctx.writeAndFlush(myMessage);
    }

    /**
     * 当通道有读取事件时会触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("服务器回复的消息: " + buffer.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
