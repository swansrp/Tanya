
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: Sharp
* @create: 2019/01/28
 **/
@Data
public class SalesmanTraderMapEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "销售人员id")
    private Integer traderId;
 
    @ApiModelProperty(value = "促销员id")
    private Integer salesmanId;
 
    @ApiModelProperty(value = "有效期起始")
    private Date startAt;
 
    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Integer valid;
}
