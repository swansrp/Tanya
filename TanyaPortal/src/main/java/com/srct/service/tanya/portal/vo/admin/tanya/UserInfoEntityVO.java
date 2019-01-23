
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: Sharp
* @create: 2019/01/23
 **/
@Data
public class UserInfoEntityVO {
 
    @ApiModelProperty(value = "用户id")
    private Integer id;
 
    @ApiModelProperty(value = "用户姓名")
    private String name;
 
    @ApiModelProperty(value = "微信id")
    private String wechatId;
 
    @ApiModelProperty(value = "电话号码")
    private String phone;
 
    @ApiModelProperty(value = "邮箱")
    private String email;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "更改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "最后登入时间")
    private Date lastAt;
 
    @ApiModelProperty(value = "有效性")
    private Integer valid;
}
