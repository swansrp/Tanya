/**
 * Title: NotificationInfoReqVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-28 18:15:55
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.vo.QueryReqVO;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class NotificationInfoReqVO extends QueryReqVO {
    private NotificationInfoVO notification;
}
