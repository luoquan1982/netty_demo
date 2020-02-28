package com.luoquan.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIOFileChanel02
 *
 * @author LuoQuan
 * @date 2020/2/27 16:48
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        //创建文件输入流
        File file = new File("d:/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过输入流对象获取对应的channel,实际的类型为FileChannelImpl
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将channel的数据读取到缓冲区
        channel.read(byteBuffer);

        //将缓冲区中的字节数据转化成字符串
        System.out.println(new String(byteBuffer.array()));

        //关闭流
        channel.close();
        fileInputStream.close();
    }
}
