/**
 * Title: ShopInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:49:22
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.role.vo.RoleInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class ShopInfoRespVO {

    @ApiModelProperty(value = "商店信息")
    private ShopInfoVO shopInfoVO;

    @ApiModelProperty(value = "所属渠道")
    private RoleInfoVO merchantRoleInfoVO;

}
