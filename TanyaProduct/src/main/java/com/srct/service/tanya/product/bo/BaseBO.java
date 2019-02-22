/**
 * Title: BaseBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author sharuopeng
 * @date 2019-02-19 09:17:04
 */
package com.srct.service.tanya.product.bo;

import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class BaseBO {

    @ApiModelProperty(value = "录入人信息")
    private UserInfo createrInfo;

    @ApiModelProperty(value = "录入人角色")
    private RoleInfo createrRole;
}
