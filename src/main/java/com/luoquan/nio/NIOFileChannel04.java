package com.luoquan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * NIOFileChannel04
 *
 * @author LuoQuan
 * @date 2020/2/27 17:34
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        //创建相关的流
        FileInputStream fileInputStream = new FileInputStream("1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("2.jpg");

        //获取相关的流对应的通道
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destinationChannel = fileOutputStream.getChannel();

        //使用transferFrom方法完成拷贝
        destinationChannel.transferFrom(sourceChannel,0,sourceChannel.size());

        //关闭相关的通道的流
        sourceChannel.close();
        destinationChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
