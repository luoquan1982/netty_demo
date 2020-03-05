package com.luoquan.netty.inboundhandlerandoutboundhander;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 类上的泛型void代表不需要状态管理
 * @author luoquan
 */
public class MybyteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        //在ReplayingDecoder 不需要判断数据是否足够读取,内部会进行判断
        out.add(in.readLong());
    }
}
