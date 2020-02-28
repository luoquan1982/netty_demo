package com.luoquan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIOServer
 *
 * @author LuoQuan
 * @date 2020/2/28 11:58
 */
public class NIOServer {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定端口666到服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel 注册到 selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectionKey数量=" + selector.keys().size());

        //循环等待客户端连接
        while (true) {
            if (0 == selector.select(1000)) {
                //这里我们等待1秒,如果没有事件发生.则继续
                System.out.println("服务器等待了1秒,无连接");
                continue;
            }

            //如果返回大于0,就获取到相关的selection集合
            //1.如果返回大于0,表示已经获取到关注的事件
            //2.selector.selectedKeys()返回的是关注事件的集合
            //通过这个方法可以反向获取到通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys的数量是(有时间发生的selectionKey)" + selectionKeys.size());

            //遍历Set<SelectionKey>集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                //获取到SectionKey
                SelectionKey key = keyIterator.next();

                //根据key对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) {
                    //如果是OP_ACCEPT,有新的客户端连接
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("客户端连接成功,生成了一个socketChannel " + socketChannel.hashCode());

                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将socketChannel注册到selector,关注事件为OP_READ,同时给socketChannel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("客户端连接后,注册的selectionKey数量=" + selector.keys().size());
                } else if (key.isReadable()) {
                    //发生了OP_READ
                    //通过key反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from客户端 " + new String(buffer.array()));
                }

                //手动从集合中移除当前的selectionKey
                keyIterator.remove();
            }
        }
    }
}
