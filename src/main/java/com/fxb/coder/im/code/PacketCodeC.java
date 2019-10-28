package com.fxb.coder.im.code;

import com.fxb.coder.im.entity.LoginRequestPacket;
import com.fxb.coder.im.entity.Packet;
import com.fxb.coder.im.inter.serializer.JSONSerializer;
import com.fxb.coder.im.inter.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.fxb.coder.im.inter.Command.LOGIN_REQUEST;
import static com.fxb.coder.im.inter.Command.LOGIN_RESPONSE;

/**
 * 请求包的编码器
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private static final Map<Byte, Serializer> serializerMap;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    static {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginRequestPacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator allocator,Packet packet) {

        // 创建ByteBuf对象: ioBuffer(),返回适配io读写相关的内存,
        // 它会尽可能创建一个直接内存,直接内存可以理解为不收jvm堆管理的内存空间,
        // 写到IO缓冲区的效果更高。
        ByteBuf byteBuf = allocator.ioBuffer();

        // 使用默认的序列化方法序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 实际的编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        /// 跳过 magic number
        byteBuf.skipBytes(4);
        // 跳过版本
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    /**
     * 获取 序列化方式
     *
     * @param serializeAlgorithm 序列化算法标识
     * @return 序列化算法
     */
    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    /**
     * 获取指令类型
     *
     * @param command 命令
     * @return 指令类型的class
     */
    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }


}
