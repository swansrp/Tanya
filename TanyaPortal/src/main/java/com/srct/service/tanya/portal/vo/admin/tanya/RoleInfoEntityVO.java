
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
public class RoleInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "角色名称")
    private String title;
 
    @ApiModelProperty(value = "角色信息表名")
    private String tableName;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
