
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: srct
* @create: 2019/01/29
 **/
@Data
public class CampaignHistoryEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "备注")
    private String comment;
 
    @ApiModelProperty(value = "上报促销员id")
    private Integer salesmanId;
 
    @ApiModelProperty(value = "上报促销活动id")
    private Integer campaignId;
 
    @ApiModelProperty(value = "促销活动数量")
    private Integer number;
 
    @ApiModelProperty(value = "上传照片地址")
    private String url;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;
 
    @ApiModelProperty(value = "该促销积分回馈")
    private Integer rewards;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
