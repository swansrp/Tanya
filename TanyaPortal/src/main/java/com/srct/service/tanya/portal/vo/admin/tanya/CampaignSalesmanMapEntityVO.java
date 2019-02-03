
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
public class CampaignSalesmanMapEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "活动id")
    private Integer campaignId;
 
    @ApiModelProperty(value = "促销人员id")
    private Integer salesmanId;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
