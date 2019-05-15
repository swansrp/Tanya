/**
 * @Title: UserInfoReqVO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.vo.user
 * @author Sharp
 * @date 2019-01-30 11:11:50
 */
package com.srct.service.tanya.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Sharp
 */
@Data
public class UserRegReqVO {

    @ApiModelProperty(name = "登录名", notes = "Sharp")
    private String username;

    @ApiModelProperty(name = "名字", notes = "Sharp", required = true)
    private String name;

    @ApiModelProperty(name = "密码", notes = "123qwe")
    private String password;

    @ApiModelProperty(name = "电话", notes = "13912345678")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(name = "邮箱", notes = "1@qq.com", required = true)
    private String email;

    @ApiModelProperty(name = "备注", notes = "超级管理员")
    private String comment;
}
