package com.luoquan.netty.dubbo.provider;

import com.luoquan.netty.dubbo.publicinterface.HelloService;

/**
 * HelloServiceImpl
 *
 * @author LuoQuan
 * @date 2020/3/9 10:04
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;
//    private  int count = 0;   //注意,每次调用都会产生不同的HelloServiceImpl

    /**
     * 当有消费方调用该方法时,就返回一个字符串
     *
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("客户端消息: " + msg);
        //根据msg 返回不同的结果
        if (null != msg) {
            return "你好,客户端,我已经收到你的消息[" + msg + "] 第" + (++count) + "次";
        }
        return "你好,客户端,我已经收到你的消息";
    }
}
