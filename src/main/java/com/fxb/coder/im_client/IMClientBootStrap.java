package com.fxb.coder.im_client;

import com.fxb.coder.im_client.handler.ClientLoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 客户端启动类
 *
 * @author fangjiaxiaobai
 * @date 2019-10-28
 * @since 1.0.0
 */
public class IMClientBootStrap {

    private static final String inetHost = "127.0.0.1";

    private static final int inetPort = 12345;

    private static final int retry = 5;

    public static void main(String[] args) {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientLoginHandler());
                    }
                });

        connect(bootstrap, retry);

    }

    private static void connect(Bootstrap bootstrap, final int retryCounts) {

        bootstrap.connect(inetHost, inetPort).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retryCounts == 0) {
                System.out.println("重试" + retry + "次之后，仍然连接失败，give up!");
            } else {
                int order = retry - retryCounts + 1;
                int delay = order << 1;
                System.out.println(new Date() + " 连接失败，第" + order + "次重连。。。");
                bootstrap.group().schedule(() -> connect(bootstrap, retryCounts - 1), delay, TimeUnit.SECONDS);
            }
        });


    }

}
