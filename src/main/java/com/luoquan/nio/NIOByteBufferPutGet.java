package com.luoquan.nio;

import java.nio.ByteBuffer;

/**
 * NIOByteBufferPutGet
 *
 * @author LuoQuan
 * @date 2020/2/27 17:47
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('罗');
        buffer.putShort((short) 8);

        //取出
        buffer.flip();
        System.out.println();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
//        System.out.println(buffer.getLong());如果将最后一次读取换成getLong则会跑出异常
    }
}
