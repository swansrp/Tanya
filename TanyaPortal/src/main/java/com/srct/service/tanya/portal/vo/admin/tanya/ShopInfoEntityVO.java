
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: Sharp
* @create: 2019/01/30
 **/
@Data
public class ShopInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "药店名称")
    private String title;
 
    @ApiModelProperty(value = "药店备注")
    private String comment;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后登入时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
