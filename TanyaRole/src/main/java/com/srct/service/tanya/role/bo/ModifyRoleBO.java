/**
 * Title: InviteRoleBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-11 00:21:54
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
public class ModifyRoleBO {

    @ApiModelProperty(value = "邀请人信息")
    private UserInfo createrInfo;

    @ApiModelProperty(value = "邀请人角色")
    private RoleInfo createrRole;

    @ApiModelProperty(value = "受邀人角色类型")
    private String roleType;

    @ApiModelProperty(value = "受邀人guid")
    private String targetGuid;

    @ApiModelProperty(value = "受邀角色id")
    private Integer id;

}
