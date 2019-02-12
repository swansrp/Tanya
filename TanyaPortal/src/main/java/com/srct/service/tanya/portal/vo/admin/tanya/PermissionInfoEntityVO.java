
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: Sharp
* @create: 2019/02/12
 **/
@Data
public class PermissionInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "父编号,本权限可能是该父编号权限的子权限")
    private Integer parentId;
 
    @ApiModelProperty(value = "父编号列表")
    private String parentIds;
 
    @ApiModelProperty(value = "权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view")
    private String permission;
 
    @ApiModelProperty(value = "资源类型，[menu|button]")
    private String resourceType;
 
    @ApiModelProperty(value = "资源路径 如：/userinfo/list")
    private String url;
 
    @ApiModelProperty(value = "权限名称")
    private String name;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
