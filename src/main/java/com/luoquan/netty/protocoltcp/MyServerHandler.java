package com.luoquan.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 处理业务的handler
 * @author luoquan
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        //接收到数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("服务器接收到信息如下:");
        System.out.println("长度:" + len);
        System.out.println("内容:" + new String(content,StandardCharsets.UTF_8));

        System.out.println("服务器接收到消息包数量:" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
