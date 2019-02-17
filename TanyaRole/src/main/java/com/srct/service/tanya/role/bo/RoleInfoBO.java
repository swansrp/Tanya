/**
 * Title: RoleInfoBO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.bo
 * @author Sharp
 * @date 2019-02-11 00:13:57
 */
package com.srct.service.tanya.role.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class RoleInfoBO extends RoleInfoBaseBO {

    @ApiModelProperty(value = "对应角色id")
    private Integer id;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "销售员数量")
    private Integer traderNumber;

}
