/**
 * Title: RoleDetailsVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.vo
 * @author Sharp
 * @date 2019-02-16 10:34:02
 */
package com.srct.service.tanya.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Sharp
 * description for update role info
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

    @ApiModelProperty(value = "修改角色联系方式")
    private String contact;

    @ApiModelProperty(value = "修改角色起始有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startAt;

    @ApiModelProperty(value = "修改角色结束有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endAt;

}
