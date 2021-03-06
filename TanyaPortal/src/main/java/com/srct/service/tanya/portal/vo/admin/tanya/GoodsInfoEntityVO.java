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
public class GoodsInfoEntityVO extends QueryReqVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "药品编码")
    private String code;

    @ApiModelProperty(value = "产品名称")
    private String title;

    @ApiModelProperty(value = "产品备注")
    private String comment;

    @ApiModelProperty(value = "产品照片地址")
    private String photoUrl;

    @ApiModelProperty(value = "生产单位")
    private String production;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "价格")
    private Double amount;

    @ApiModelProperty(value = "渠道id")
    private Integer merchantId;

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
