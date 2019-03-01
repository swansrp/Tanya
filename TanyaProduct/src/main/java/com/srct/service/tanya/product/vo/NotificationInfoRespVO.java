/**
 * Title: NotificationInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-28 18:17:42
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.role.vo.RoleInfoVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class NotificationInfoRespVO {

    private NotificationInfoVO notificationInfoVO;

    @ApiModelProperty(value = "药厂信息")
    private RoleInfoVO factoryInfoVO;

}
