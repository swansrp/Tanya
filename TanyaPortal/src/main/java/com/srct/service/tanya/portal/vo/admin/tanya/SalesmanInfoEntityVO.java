
package com.srct.service.tanya.portal.vo.admin.tanya;

import com.srct.service.vo.QueryReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: sharuopeng
 **/
@Data
public class SalesmanInfoEntityVO extends QueryReqVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "角色名称")
    private String title;
 
    @ApiModelProperty(value = "联系方式")
    private String contact;
 
    @ApiModelProperty(value = "角色备注")
    private String comment;
 
    @ApiModelProperty(value = "人员信息")
    private Integer userId;
 
    @ApiModelProperty(value = "促销员积分")
    private Integer rewards;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
