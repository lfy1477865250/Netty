package com.fy.Netty.DubboRPC.provider;

import com.fy.Netty.DubboRPC.publicInterface.HelloService;

//当有消费者调用该方法 就返回一个结果
public class HelloServiceImpl implements HelloService {
    private static int count = 0;
    @Override
    public String hello(String msg) {
        System.out.println("----------服务端开始接受消息----------");
        if(msg!=null){
            return "客户端:  "+msg+"  次数="+(++count);
        }else {
            return "客户端：";
        }
    }
}
