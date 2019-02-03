
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: srct
* @create: 2019/02/03
 **/
@Data
public class GoodsFactoryMerchantMapEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
 
    @ApiModelProperty(value = "药厂id")
    private Integer factoryId;
 
    @ApiModelProperty(value = "药厂渠道关系id")
    private Integer factoryMetchatMapId;
 
    @ApiModelProperty(value = "有效期起始")
    private Date startAt;
 
    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
