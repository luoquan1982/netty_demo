package com.luoquan.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIOFileChanel
 *
 * @author LuoQuan
 * @date 2020/2/27 12:05
 */
public class NIOFileChanel01 {
    public static void main(String[] args) throws IOException {
        String str = "netty学习真开心啊";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:/file01.txt");

        //通过fileOutputStream 获取对应的channel
        //这个channel真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将我们的str 放入到 byteBuffer中
        byteBuffer.put(str.getBytes());

        //对byteBuffer 进行反转(flip),重要,别忘了
        byteBuffer.flip();

        //将byteBuffer中的数据写入到channel中
        channel.write(byteBuffer);

        channel.close();
        fileOutputStream.close();
    }
}
