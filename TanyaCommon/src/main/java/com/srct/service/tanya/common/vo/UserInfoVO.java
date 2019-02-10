/**
 * Title: UserInfoVO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.vo
 * @author Sharp
 * @date 2019-02-10 12:08:16
 */
package com.srct.service.tanya.common.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class UserInfoVO {

    @ApiModelProperty(name = "用户名", notes = "Sharp", required = true)
    private String name;

    @ApiModelProperty(name = "电话", notes = "13912345678", required = true)
    private String phone;

    @NotBlank(message = "email cant be null")
    @ApiModelProperty(name = "邮箱", notes = "1@qq.com", required = true)
    private String email;

    @ApiModelProperty(name = "备注", notes = "超级管理员", required = false)
    private String comment;

    @ApiModelProperty(name = "角色id", notes = "管理员", required = false)
    private Integer roleId;

}
