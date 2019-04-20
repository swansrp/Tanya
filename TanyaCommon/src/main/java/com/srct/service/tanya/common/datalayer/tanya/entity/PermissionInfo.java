package com.srct.service.tanya.common.datalayer.tanya.entity;

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
public class PermissionInfo {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "父编号,本权限可能是该父编号权限的子权限")
    private Integer parentId;

    @ApiModelProperty(value = "")
    private String permission;

    @ApiModelProperty(value = "资源类型，[menu|button]")
    private String resourceType;

    @ApiModelProperty(value = "资源路径 如：/userinfo/list")
    private String path;

    @ApiModelProperty(value = "图片地址")
    private String iconUrl;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;

    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
