package com.luoquan.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * MyTextWebSocketFrameHandler
 *
 * @author LuoQuan
 * @date 2020/3/4 12:16
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当web客户端连接后,就会触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一的值,LongText是唯一的,ShortText就不是唯一的了
        System.out.println("handlerAdded被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生:" + cause.getMessage());
        ctx.close();    //关闭连接
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用" + ctx.channel().id().asLongText());
    }

    /**
     * 这里TextWebSocketFrame类型,标识一个文本帧(frame)
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息 " + msg.text());

        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + msg.text()));
    }
}
