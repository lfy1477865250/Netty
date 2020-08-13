package com.fy.Netty.DubboRPC.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    //创建线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler client;

    //代理模式方法 获取一个代理对象
    //发送一次 调用一次
    public Object getBean(Class<?> serviceClass,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},(proxy,methods,args)->{
                    /**
                     * invoke
                     * proxy：指代我们所代理的那个真实对象
                     * methods:指代的是我们所要调用真实对象的某个方法的Method对象
                     * args:指代的是调用真实对象某个方法时接受的参数
                     */
           if(client == null ){
               initClient();
           }
           client.setPara(providerName+args[0]);
           //submit返回线程返回的数据 get()获取的就是call() 方法中的result
           return executor.submit(client).get();
        });
    }

    //初始化客户端
    private static void initClient(){
        client = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("Encoder",new StringEncoder());
                            pipeline.addLast("Decoder",new StringDecoder());
                            pipeline.addLast(client);
                        }
                    });
            bootstrap.connect("127.0.0.1",7001).sync();
            System.out.println("客户端启动");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
