/**
 * Title: QuerySubordinateBO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-3 16:55
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.role.bo
 */
package com.srct.service.tanya.role.bo;

import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.vo.QueryReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuerySubordinateBO extends QueryReqVO {
    private UserInfo userInfo;
    private Boolean targetIsExisted;
}
