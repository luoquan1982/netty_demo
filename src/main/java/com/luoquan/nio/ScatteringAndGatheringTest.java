package com.luoquan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 客户端
 * ScatteringAndGatheringTest
 * scattering(分散):将数据写入到buffer时,可以采用buffer数组,依次写入
 * Gathering(聚合):从buffer读取数据时,可以采用buffer数组,依次读
 *
 * @author LuoQuan
 * @date 2020/2/27 19:00
 */
public class ScatteringAndGatheringTest {
    /**
     * 消除47行的while警告
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        //这次使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建一个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        //假定从客户端接收8个字节
        int messageLength = 8;

        //循环读取
        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                //累计读取到的字节数
                byteRead += read;
                System.out.println("byteRead=" + byteRead);
                //使用流打印,看看当前的buffer的position和limit
                Arrays.stream(byteBuffers)
                        .map(buffer -> "position=" + buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);
            }

            //将所有的buffer进行反转
            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            //将所有的buffer进行clear操作
            Arrays.stream(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + ",byteWrite=" + byteWrite + ",messageLenght=" + messageLength);
        }
    }
}
