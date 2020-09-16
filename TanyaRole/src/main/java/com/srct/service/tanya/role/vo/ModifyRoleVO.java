/**
 * Title: InviteRoleVO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 *
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.vo
 * @author Sharp
 * @date 2019-02-12 00:34:35
 */
package com.srct.service.tanya.role.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class ModifyRoleVO {

    @ApiModelProperty(value = "邀请角色类型")
    private String roleType;

    @ApiModelProperty(value = "邀请角色id")
    private Integer id;

    @ApiModelProperty(value = "邀请人员id")
    private Integer userId;

}
