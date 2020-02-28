package com.luoquan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIOFileChannel03
 *
 * @author LuoQuan
 * @date 2020/2/27 17:06
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("11.txt");
        FileChannel channel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            //这里有个重要的操作不要忘了
            byteBuffer.clear();//清空buffer,非常重要
            int read = channel01.read(byteBuffer);
            if (-1 == read) {
                break;
            }
            byteBuffer.flip();
            channel02.write(byteBuffer);
        }

        //关闭相关的管道和流
        channel01.close();
        channel02.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
