/**
 * Title: RoleInfoVO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.vo
 * @author Sharp
 * @date 2019-02-11 00:35:19
 */
package com.srct.service.tanya.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Sharp
 *
 */
@Data
public class RoleInfoVO {

    @ApiModelProperty(value = "角色类型")
    private String roleType;

    @ApiModelProperty(value = "对应角色id")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String title;

    @ApiModelProperty(value = "角色联系方式")
    private String contact;

    @ApiModelProperty(value = "角色备注")
    private String comment;

    @ApiModelProperty(value = "人员信息")
    private Integer userId;

    @ApiModelProperty(value = "人员名称")
    private String userName;

    @ApiModelProperty(value = "人员备注")
    private String userComment;

    @ApiModelProperty(value = "有效期起始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endAt;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "销售员数量")
    private Integer traderNumber;
}
