package com.luoquan.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MapperedByteBuffer
 * MappedByteBuffer
 * 可以让文件直接在内存(堆外内存)中修改,操作系统不需要拷贝一次
 *
 * @author LuoQuan
 * @date 2020/2/27 18:27
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("11.txt", "rw");
        //获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();

        /*
         * 参数意义
         * 参数1、FileChannel.MapMode.READ_WRITE:读写模式
         * 参数2、0:可以直接修改的起始位置
         * 参数3、5:映射到内存的大小(不是索引位置),即将文件1.txt的多少个字节映射到内存
         * 可以直接修改的范围就是0-5
         * 实际类型是DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');

        randomAccessFile.close();
        System.out.println("修改成功~~~");
    }
}
