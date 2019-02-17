/**
 * Title: GetRoleDetailsBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-17 21:44:14
 */
package com.srct.service.tanya.role.bo;

import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class GetRoleDetailsBO {

    @ApiModelProperty(value = "获取人信息")
    private UserInfo createrInfo;

    @ApiModelProperty(value = "邀请人角色")
    private RoleInfo createrRole;

    @ApiModelProperty(value = "获取角色id")
    private Integer id;
}
