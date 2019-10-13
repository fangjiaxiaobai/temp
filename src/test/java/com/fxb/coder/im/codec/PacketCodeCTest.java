package com.fxb.coder.im.codec;

import com.fxb.coder.im.code.PacketCodeC;
import com.fxb.coder.im.entity.LoginRequestPacket;
import com.fxb.coder.im.entity.Packet;
import com.fxb.coder.im.inter.serializer.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 序列化的测试脚本
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
public class PacketCodeCTest {

    @Test
    public void encode() {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUserId(1);
        loginRequestPacket.setUsername("方小白");
        loginRequestPacket.setPassword("fxb");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decode = packetCodeC.decode(byteBuf);

        JSONSerializer jsonSerializer = new JSONSerializer();
        Assertions.assertArrayEquals(jsonSerializer.serialize(loginRequestPacket),
                jsonSerializer.serialize(decode));


    }


}
