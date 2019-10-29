package com.fxb.coder.im_client.handler;

import com.fxb.coder.im.code.PacketCodeC;
import com.fxb.coder.im.entity.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author fangjiaxiaobai
 * @date 2019-10-28
 * @since 1.0.0
 */
public class ClientLoginHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("客户端开始登录....");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(1);
        loginRequestPacket.setUsername("fxb");
        loginRequestPacket.setPassword("fxb");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(buffer);

        if (packet instanceof LoginRequestPacket) {

            LoginResponsePacket loginRequestPacket = (LoginResponsePacket) packet;

            if (loginRequestPacket.isSuccess()) {
                System.out.println("客户端登录成功");
            } else {
                System.out.println("客户端登录失败....");
            }
        } else if (packet instanceof MessageResponsePacket) {
            // 如果是消息的响应包
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            String message = messageResponsePacket.getMessage();
            System.out.println(new Date() + ": 收到服务端消息是: " + message);
        }


    }
}
