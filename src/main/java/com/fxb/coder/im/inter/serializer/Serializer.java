package com.fxb.coder.im.inter.serializer;

/**
 * 序列化的接口
 * <p>
 * 定义一种规则: 规定其如何把一个java对象转换成二进制数据,即java对象的序列化
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
public interface Serializer {


    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return 使用的序列化算法的标识
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换称为二进制
     *
     * @param object java对象
     * @return 二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     *
     * @param clazz java转换的类型
     * @param bytes 二进制
     * @param <T>   java的类型
     * @return java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
