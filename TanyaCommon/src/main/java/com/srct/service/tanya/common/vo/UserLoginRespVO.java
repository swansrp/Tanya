/**
 * @Title: UserRegRespVO.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.vo.user
 * @author Sharp
 * @date 2019-01-30 11:21:18
 */
package com.srct.service.tanya.common.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class UserLoginRespVO {

    @NotBlank(message = "sn cant be null")
    @ApiModelProperty("用户SN")
    private String sn;
    
    @ApiModelProperty("用户信息录入")
    private boolean registered;

    @ApiModelProperty("用户等级")
    private List<String> role;
    
    

}
