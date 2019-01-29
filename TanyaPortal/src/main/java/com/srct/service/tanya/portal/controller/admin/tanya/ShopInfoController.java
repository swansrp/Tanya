/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: srct   
 * @date: 2019/01/29
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.ShopInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ShopInfo")
@RestController("tanyaShopInfoController")
@RequestMapping(value = "/portal/admin/tanya/shopinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class ShopInfoController {

    @Autowired
    ShopInfoDao shopInfoDao;

    @ApiOperation(value = "更新ShopInfo", notes = "传入ShopInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "ShopInfoEntityVO", name = "vo", value = "ShopInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateShopInfo(@RequestBody ShopInfoEntityVO vo) {
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(vo, shopInfo);
        Integer id = shopInfoDao.updateShopInfo(shopInfo);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询ShopInfo", notes = "传入ShopInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "ShopInfoEntityVO", name = "vo", value = "ShopInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<ShopInfo>>.Resp> getShopInfoSelective(
            @RequestBody ShopInfoEntityVO vo
            ) {
        List<ShopInfo> res = new ArrayList<>();
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(vo, shopInfo);
        res.addAll(shopInfoDao.getShopInfoSelective(shopInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询ShopInfo", notes = "返回id对应的ShopInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "ShopInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<ShopInfo>>.Resp> getShopInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<ShopInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(shopInfoDao.getAllShopInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(shopInfoDao.getShopInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除ShopInfo", notes = "软删除主键为id的ShopInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "ShopInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delShopInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = shopInfoDao.updateShopInfo(shopInfo);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
