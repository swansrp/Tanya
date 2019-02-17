/**
 * Title: RoleInfoBaseBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-16 10:48:53
 */
package com.srct.service.tanya.role.bo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class RoleInfoBaseBO {

    @ApiModelProperty(value = "角色类型")
    private String roleType;

    @ApiModelProperty(value = "角色名称")
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
