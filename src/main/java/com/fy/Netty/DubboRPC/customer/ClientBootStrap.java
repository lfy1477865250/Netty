package com.fy.Netty.DubboRPC.customer;


import com.fy.Netty.DubboRPC.netty.NettyClient;
import com.fy.Netty.DubboRPC.publicInterface.HelloService;

public class ClientBootStrap {
    //定义协议头
    private static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception{
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        //通过代理对象调用服务提供者的方法
        for (;;){
            Thread.sleep(2000);
            String msg = service.hello("Fyyyy");
            System.out.println("调用返回的结果"+msg);
        }
    }
}
