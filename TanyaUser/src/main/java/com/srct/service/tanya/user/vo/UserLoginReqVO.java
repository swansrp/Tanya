/**
 * @Title: UserLoginReqVO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.vo.user
 * @author Sharp
 * @date 2019-01-30 12:08:55
 */
package com.srct.service.tanya.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class UserLoginReqVO {

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户wechatCode")
    private String wechatCode;

}
