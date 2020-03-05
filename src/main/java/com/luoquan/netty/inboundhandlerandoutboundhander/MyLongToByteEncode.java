package com.luoquan.netty.inboundhandlerandoutboundhander;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author luoquan
 */
public class MyLongToByteEncode extends MessageToByteEncoder<Long> {
    /**
     * 编码的方法
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncode类中的encode方法被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
