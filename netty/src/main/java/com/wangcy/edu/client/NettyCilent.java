package com.wangcy.edu.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName NettyCilent
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class NettyCilent {

    //客户端给服务端发送数据
    public static void main(String[] args) throws InterruptedException {

        //1创建连接池对象
        NioEventLoopGroup bootstrap = new NioEventLoopGroup();

        //2创建客户端的启动引导类  Bootsrip
        Bootstrap bootstrap1 = new Bootstrap();

        //3配置启动引导类
        bootstrap1.group(bootstrap)
                //设置通道
        .channel(NioSocketChannel.class)
                //设置channel监听
        .handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel) throws Exception {
                //设置编码
                ChannelPipeline entries = channel.pipeline().addLast(new StringEncoder());
            }
        });

        //4使用启动引导类连接服务器  获取一个channel
        Channel channel = bootstrap1.connect("127.0.0.1", 9999).channel();

        //5循环写入 数据给服务器
        while (true){
            channel.writeAndFlush("hello server 。。。。。。。");
            Thread.sleep(2000);
        }
    }
}
