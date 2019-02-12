
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
public class UserInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "系统唯一标识")
    private String guid;
 
    @ApiModelProperty(value = "用户名")
    private String username;
 
    @ApiModelProperty(value = "登录密码")
    private String password;
 
    @ApiModelProperty(value = "真实姓名")
    private String name;
 
    @ApiModelProperty(value = "身份证号码")
    private String idCardNum;
 
    @ApiModelProperty(value = "微信openId")
    private String wechatId;
 
    @ApiModelProperty(value = "电话号码")
    private String phone;
 
    @ApiModelProperty(value = "邮箱号码")
    private String email;
 
    @ApiModelProperty(value = "角色备注")
    private String comment;
 
    @ApiModelProperty(value = "用户状态：0:正常状态,1：用户被锁定")
    private String state;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "最后登入时间")
    private Date lastAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
