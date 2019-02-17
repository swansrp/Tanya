/**
 * Title: UpdateRoleBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-16 10:44:34
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
public class UpdateRoleInfoBO extends RoleInfoBaseBO {

    @ApiModelProperty(value = "修改角色信息目标id")
    private Integer targetId;

    @ApiModelProperty(value = "上级id")
    private Integer superiorId;

    @ApiModelProperty(value = "创建人信息")
    private UserInfo createrInfo;

    @ApiModelProperty(value = "创建人角色")
    private RoleInfo createrRole;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "销售员数量")
    private Integer traderNumber;
}
