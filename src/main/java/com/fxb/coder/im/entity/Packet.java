package com.fxb.coder.im.entity;

import lombok.Data;

/**
 * 所有的指令数据包都必须实现这个方法
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
@Data
public abstract class Packet {

    /**
     * 协议版本号
     */
    private Byte version = 1;

    /**
     * 指令
     *
     * @return 指令
     */
    public abstract Byte getCommand();

}
