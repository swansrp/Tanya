/**
 * Title: GoodsController.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author Sharp
 * @date 2019-02-17 21:14:54
 */
package com.srct.service.tanya.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.GoodsInfoBO;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sharp
 *
 */
@Api(value = "GoodsController")
@RestController("GoodsController")
@RequestMapping(value = "/goods")
@CrossOrigin(origins = "*")
public class GoodsController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "新增/更新药品", notes = "只有factory、trader等级可以添加药品 若传入id则为更新")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<GoodsInfoVO>>.Resp> createRole(@RequestBody GoodsInfoVO vo) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***createGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        GoodsInfoBO goods = new GoodsInfoBO();
        BeanUtil.copyProperties(vo, goods);
        goods.setCreaterInfo(info);
        goods.setCreaterRole(role);
        List<GoodsInfoVO> goodsInfoVOList = goodsService.updateGoodsInfo(goods);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

}
