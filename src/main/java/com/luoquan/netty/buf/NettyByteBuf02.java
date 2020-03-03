package com.luoquan.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * NettyByteBuf02
 *
 * @author LuoQuan
 * @date 2020/3/3 13:02
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {

        //另一种方式 通过Unpooled创建byteBuf
        ByteBuf buffer = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        //使用相关的方法
        if (buffer.hasArray()) {
            byte[] content = buffer.array();
            //将content转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println(buffer.arrayOffset());

            System.out.println(buffer.readerIndex());
            System.out.println(buffer.writerIndex());
            System.out.println(buffer.capacity());

            //可读取的字节数
            int readableBytes = buffer.readableBytes();
            System.out.println(readableBytes);

            //使用for循环取出各个字节
            for (int i = 0; i < readableBytes; i++) {
                System.out.print((char) buffer.getByte(i) + " ");
            }

            //按照某个范围读取
            System.out.println("\n" + buffer.getCharSequence(0, 4, CharsetUtil.UTF_8));
        }
    }
}
