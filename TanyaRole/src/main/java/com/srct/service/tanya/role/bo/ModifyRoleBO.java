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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class ModifyRoleBO {

    @ApiModelProperty(value = "邀请人guid")
    private String sourceGuid;

    @ApiModelProperty(value = "角色类型")
    private String roleType;

    @ApiModelProperty(value = "受邀人guid")
    private String targetGuid;

    @ApiModelProperty(value = "对应角色id")
    private Integer id;

}
