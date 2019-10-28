package com.fxb.coder.im.entity;

import com.fxb.coder.im.inter.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录响应包
 *
 * @author fangjiaxiaobai
 * @date 2019-10-28
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {


    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
