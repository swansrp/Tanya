/**
 * Title: RoleDetailsVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.vo
 * @author Sharp
 * @date 2019-02-16 10:34:02
 */
package com.srct.service.tanya.role.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class RoleDetailsVO {

    @ApiModelProperty(value = "修改角色类型")
    private String roleType;

    @ApiModelProperty(value = "修改角色id")
    private Integer id;

    @ApiModelProperty(value = "修改角色title")
    private String title;

    @ApiModelProperty(value = "修改角色comment")
    private String comment;

    @ApiModelProperty(value = "修改角色起始有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startAt;

    @ApiModelProperty(value = "修改角色结束有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endAt;

}
