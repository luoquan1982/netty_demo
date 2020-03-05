package com.luoquan.netty.inboundhandlerandoutboundhander;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author luoquan
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("服务器的IP:" + ctx.channel().remoteAddress());
        System.out.println("收到服务器的消息:" + msg);
    }

    /**
     * 重写channelActive发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        //ctx.writeAndFlush(Unpooled.copiedBuffer(""));
        ctx.writeAndFlush(123456L); //发送的是一个long

        //分析
        //1."abcdabcdabcdabcd"是16个字节
        //2.该处理器的前一个handler是MyLongToByteEncoder
        //3.MyLongToByteEncoder 父类 MessageToByteEncoder
        //4.父类MessageToByteEncoder
        //ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
    }
}
