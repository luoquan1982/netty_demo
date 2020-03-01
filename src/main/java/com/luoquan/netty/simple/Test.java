package com.luoquan.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * Test
 *
 * @author LuoQuan
 * @date 2020/3/1 18:46
 */
public class Test {
    public static void main(String[] args) {
        //获取CPU核心数
        System.out.println(NettyRuntime.availableProcessors());
    }
}
