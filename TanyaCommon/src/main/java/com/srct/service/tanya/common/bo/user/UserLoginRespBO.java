/**
 * @Title: UserLoginRespBO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.bo.user
 * @author Sharp
 * @date 2019-01-30 15:48:45
 */
package com.srct.service.tanya.common.bo.user;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class UserLoginRespBO {

    @ApiModelProperty("用户guid")
    private String guid;

    @NotBlank(message = "sn cant be null")
    @ApiModelProperty("用户SN")
    private String sn;

    @ApiModelProperty("用户等级")
    private List<String> role;

}
