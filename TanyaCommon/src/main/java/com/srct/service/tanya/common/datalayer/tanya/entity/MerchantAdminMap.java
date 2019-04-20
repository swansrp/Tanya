package com.srct.service.tanya.common.datalayer.tanya.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实体类
 * 根据需要删减无效参数
 *
 * @author: sharuopeng
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MerchantAdminMap {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "渠道id")
    private Integer merchantId;

    @ApiModelProperty(value = "管理员id")
    private Integer adminId;

    @ApiModelProperty(value = "药厂数量")
    private Integer factoryNumber;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    @ApiModelProperty(value = "是否签约")
    private Byte sign;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "最后登入时间")
    private Date updateAt;

    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
