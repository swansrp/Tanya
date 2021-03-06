/**
 * @Title: UserRegReqBO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.bo.user
 * @author Sharp
 * @date 2019-01-30 15:48:31
 */
package com.srct.service.tanya.common.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Sharp
 */
@Data
public class UserRegReqBO {

    @ApiModelProperty(name = "登录名", notes = "非前端输入,默认为邮箱", required = true)
    private String username;

    @ApiModelProperty(name = "密码", notes = "123qwe", required = false)
    private String password;

    @ApiModelProperty(name = "系统guid", notes = "非前端输入", required = false)
    private String guid;

    @ApiModelProperty(name = "用户名", notes = "Sharp", required = false)
    private String name;

    @ApiModelProperty(name = "微信openId", notes = "非前端输入", required = false)
    private String wechatId;

    @ApiModelProperty(name = "电话", notes = "13912345678", required = false)
    private String phone;

    @NotBlank(message = "email cant be null")
    @ApiModelProperty(name = "邮箱", notes = "1@qq.com", required = true)
    private String email;

    @ApiModelProperty(name = "备注", notes = "超级管理员", required = false)
    private String comment;
}
