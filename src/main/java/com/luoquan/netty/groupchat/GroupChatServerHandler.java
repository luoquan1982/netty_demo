package com.luoquan.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * GroupChatServerHandler
 *
 * @author LuoQuan
 * @date 2020/3/3 17:15
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 点对点聊天,可以使用一个hashMap管理
     */
//    public Map<String, Channel> channels = new HashMap<>();

    /**
     * 定义一个channel组,管理所有的channel
     * GlobalEventExecutor.INSTANCE是一个全局执行器,是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm::ss");

    /**
     * handlerAdded 表示连接建立,一旦连接,第一个被执行
     * 将当前channel加入到channelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其它在线的客户端
        //该方法会将channelGroup中所有的channel遍历并发送消息,我们不需要自己遍历
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 加入聊天" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);

//        channels.put("id100", channel);
    }

    /**
     * 表示channel处于活动的状态,提示***上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~\n");
    }

    /**
     * 表示channel处于不活动状态,提示***离线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~\n");
    }

    /**
     * 断开连接,将**客户离开信息推送给当前在线的客户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 离线了\n");
        System.out.println("channelGroup size " + channelGroup.size());
    }

    /**
     * 处理异常,关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常,关闭通道
        ctx.close();
    }

    /**
     * 业务处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //获取到当前channel
        Channel channel = ctx.channel();

        //这时我们遍历channelGroup,根据不同的情况,会送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                //判断,如果不是当前的channel,则转发消息(排除自己)
                ch.writeAndFlush("[客户] " + channel.remoteAddress() + " 发送消息 " + msg + "\n");
            } else {
                //把自己发送的消息回显
                ch.writeAndFlush("[自己]发送了消息 " + msg + "\n");
            }
        });
    }
}
