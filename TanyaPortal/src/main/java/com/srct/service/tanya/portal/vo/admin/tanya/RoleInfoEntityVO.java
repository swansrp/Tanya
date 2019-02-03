
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
public class RoleInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "角色名称")
    private String role;
 
    @ApiModelProperty(value = "角色信息模块名")
    private String moduleName;
 
    @ApiModelProperty(value = "角色描述,UI界面显示使用")
    private String comment;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
