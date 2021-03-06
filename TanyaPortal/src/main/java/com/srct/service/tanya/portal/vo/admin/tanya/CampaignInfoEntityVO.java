package com.srct.service.tanya.portal.vo.admin.tanya;

import com.srct.service.vo.QueryReqVO;
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
public class CampaignInfoEntityVO extends QueryReqVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "促销活动名称")
    private String title;

    @ApiModelProperty(value = "促销活动备注")
    private String comment;

    @ApiModelProperty(value = "发起销售人员id")
    private Integer traderId;

    @ApiModelProperty(value = "促销商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "促销单位")
    private Integer unitAmount;

    @ApiModelProperty(value = "促销积分")
    private Integer reward;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;

    @ApiModelProperty(value = "确认状态")
    private Byte confirmStatus;

    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;

    @ApiModelProperty(value = "确认者id")
    private Integer confirmBy;

    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
