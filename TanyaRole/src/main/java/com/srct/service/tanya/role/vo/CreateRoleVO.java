/**
 * Title: CreateRoleVO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.vo
 * @author Sharp
 * @date 2019-02-10 23:17:22
 */
package com.srct.service.tanya.role.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class CreateRoleVO {

    @ApiModelProperty(value = "角色类型", notes = "only for superAdmin")
    private String roleType;

    @ApiModelProperty(value = "角色名称", required = true)
    private String title;

    @ApiModelProperty(value = "角色备注")
    private String comment;

    @ApiModelProperty(value = "人员信息")
    private Integer userId;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

}
