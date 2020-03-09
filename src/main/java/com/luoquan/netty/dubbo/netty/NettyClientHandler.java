package com.luoquan.netty.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * NettyClientHandler
 *
 * @author LuoQuan
 * @date 2020/3/9 10:44
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;  //上下文
    private String result;  //返回的结果
    private String para;    //客户端调用方法时,传入的参数

    /**
     * 与服务器的链接创建成功后,就会被调用,这个方法是第一个被调用的(1)
     * 并且只会被调用一次
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive被调用");
        context = ctx;  //因为我们在其他方法会使用到ctx
    }

    /**
     * 收到服务器的数据后,就会调用此方法(4)
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead被调用");
        result = msg.toString();
        notify();   //唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用,发送数据给服务器,-->wait 等待被唤醒(channelRead) --> 返回结果(3)->(4)->返回
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call 被调用");
        context.writeAndFlush(para);
        //进行wait
        wait(); //等待channelRead方法获取到服务器的结果后,唤醒
        System.out.println("call wait后 被调用");
        return result;  //服务方返回的结果
    }

    //(2)
    void setPara(String para){
        System.out.println(" setPara ");
        this.para = para;
    }
}
