package com.fxb.coder.im.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.fxb.coder.im.inter.Command.LOGIN_REQUEST;

/**
 * 登录请求指令
 *
 * @author fangjiaxiaobai
 * @date 2019-10-13
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 用户的姓名
     */
    private String username;

    /**
     * 用户的密码
     */
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
