package com.luoquan.netty.dubbo.publicinterface;

/**
 * HelloService
 * 这个是接口,是服务提供方和服务消费方都需要的
 * @author LuoQuan
 * @date 2020/3/9 10:00
 */
public interface HelloService {

    String hello(String msg);
}
