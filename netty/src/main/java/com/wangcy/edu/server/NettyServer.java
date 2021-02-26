package com.wangcy.edu.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName NettyServer
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class NettyServer {

    //接收客户端请求打印
    public static void main(String[] args) throws InterruptedException {

        //1 创建两个线程池对象
        //负责接收请求
        NioEventLoopGroup bosssGroup = new NioEventLoopGroup();
        //负责处理用户的io读写操作
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //2创建启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //3设置启动引导类
        serverBootstrap.group(bosssGroup,workGroup)
                //设置当前的一个通道类型
        .channel(NioServerSocketChannel.class)
                //绑定一个初始化监听
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                //获取pipeLine
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                //绑定编码
                pipeline.addFirst(new StringEncoder());
                pipeline.addLast(new StringDecoder());
                //绑定我们的业务逻辑
                pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                        //获取入栈信息，打印客户端传递的数据
                        System.out.println(s);
                    }
                });
            }
        });


        //4启动引导类 绑定端口
        ChannelFuture future = serverBootstrap.bind(9999).sync();

        //5关闭通道
        future.channel().closeFuture().sync();
    }
}
