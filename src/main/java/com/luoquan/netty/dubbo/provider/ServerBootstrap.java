package com.luoquan.netty.dubbo.provider;

import com.luoquan.netty.dubbo.netty.NettyServer;

/**
 * ServerBootstrap
 * ServerBootstrap会启动一个服务提供者,就是NettyServer
 * @author LuoQuan
 * @date 2020/3/9 10:10
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }
}
