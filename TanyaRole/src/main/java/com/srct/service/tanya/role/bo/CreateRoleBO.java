/**
 * Title: CreateServiceBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-10 23:44:18
 */
package com.srct.service.tanya.role.bo;

import java.util.Date;

import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class CreateRoleBO {

    @ApiModelProperty(value = "创建人信息")
    private UserInfo createrInfo;

    @ApiModelProperty(value = "创建人角色")
    private RoleInfo createrRole;

    @ApiModelProperty(value = "角色类型")
    private String roleType;

    @ApiModelProperty(value = "角色名称")
    private String title;

    @ApiModelProperty(value = "角色备注")
    private String comment;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
}
