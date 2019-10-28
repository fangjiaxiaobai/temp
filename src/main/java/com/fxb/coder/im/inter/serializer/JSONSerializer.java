package com.fxb.coder.im.inter.serializer;

import com.alibaba.fastjson.JSON;

/**
 * Json序列化实现
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
public class JSONSerializer implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
