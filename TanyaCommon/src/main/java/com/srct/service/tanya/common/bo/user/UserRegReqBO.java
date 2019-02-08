/**
 * @Title: UserRegReqBO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.bo.user
 * @author Sharp
 * @date 2019-01-30 15:48:31
 */
package com.srct.service.tanya.common.bo.user;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class UserRegReqBO {

    @ApiModelProperty(name = "名字", notes = "Sharp", required = true)
    private String username;

    @ApiModelProperty(name = "密码", notes = "123qwe", required = false)
    private String password;

    @ApiModelProperty(name = "系统guid", notes = "非前端输入", required = false)
    private String guid;

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
