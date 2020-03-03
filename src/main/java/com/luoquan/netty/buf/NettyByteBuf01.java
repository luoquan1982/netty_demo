package com.luoquan.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * NettyByteBuf01
 *
 * @author LuoQuan
 * @date 2020/3/3 12:24
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {

        //创建一个byteBuf
        //说明
        //1.创建一个对象,该对象包含一个数组arr.是一个byte[10]
        //2.在netty的buffer中,不需要使用flip进行反转
        //底层维护了readerIndex和writerIndex
        //3.通过readIndex,writeIndex和capacity将buffer分成了三个区域
        //0--readIndex:已读取过的区域.
        //readIndex--writeIndex:可读区域
        //writeIndex--capacity:可写区域
        ByteBuf buffer = Unpooled.buffer(10);
        int capacity = buffer.capacity();

        for (int i = 0; i < capacity; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < capacity; i++) {
            //getByte不会引起readerIndex的变化
            System.out.print(buffer.getByte(i) + " ");
        }

        System.out.println("\n======================================");

        for (int i = 0; i < capacity; i++) {
            //readByte会引起readerIndex的变化
            System.out.print(buffer.readByte() + " ");
        }
    }
}
