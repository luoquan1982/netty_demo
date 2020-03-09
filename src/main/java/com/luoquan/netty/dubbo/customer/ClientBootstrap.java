package com.luoquan.netty.dubbo.customer;

import com.luoquan.netty.dubbo.netty.NettyClient;
import com.luoquan.netty.dubbo.publicinterface.HelloService;

/**
 * ClientBootstrap
 *
 * @author LuoQuan
 * @date 2020/3/9 11:43
 */
public class ClientBootstrap {

    /**
     * 协议头
     */
    public static final String PROVIDER_NAME = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException{

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, PROVIDER_NAME);

        for (; ; ) {
            Thread.sleep(3 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果: " + res);
        }
    }
}
