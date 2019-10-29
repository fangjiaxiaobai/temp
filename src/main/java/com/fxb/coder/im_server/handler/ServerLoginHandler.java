package com.fxb.coder.im_server.handler;

import com.fxb.coder.im.code.PacketCodeC;
import com.fxb.coder.im.entity.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务端登录的实现逻辑。
 *
 * @author fangjiaxiaobai
 * @date 2019-10-28
 * @since 1.0.0
 */
public class ServerLoginHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" 客户端开始登录....");

        ByteBuf buf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(buf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                ctx.channel().writeAndFlush(loginResponsePacket);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("用户名，密码校验失败.");
            }
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            String message = messageRequestPacket.getMessage();

            System.out.println("收到客户端的消息:" + message);
            // 返回给客户端的消息
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage(message);
            ByteBuf encode = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(encode);


        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
