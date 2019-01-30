/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/01/30
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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.GoodsInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "GoodsInfo")
@RestController("tanyaGoodsInfoController")
@RequestMapping(value = "/portal/admin/tanya/goodsinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class GoodsInfoController {

    @Autowired
    GoodsInfoDao goodsInfoDao;

    @ApiOperation(value = "更新GoodsInfo", notes = "传入GoodsInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "GoodsInfoEntityVO", name = "vo", value = "GoodsInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateGoodsInfo(@RequestBody GoodsInfoEntityVO vo) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtil.copyProperties(vo, goodsInfo);
        Integer id = goodsInfoDao.updateGoodsInfo(goodsInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询GoodsInfo", notes = "传入GoodsInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "GoodsInfoEntityVO", name = "vo", value = "GoodsInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<GoodsInfo>>.Resp> getGoodsInfoSelective(
            @RequestBody GoodsInfoEntityVO vo
            ) {
        List<GoodsInfo> res = new ArrayList<>();
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtil.copyProperties(vo, goodsInfo);
        res.addAll(goodsInfoDao.getGoodsInfoSelective(goodsInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询GoodsInfo", notes = "返回id对应的GoodsInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "GoodsInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<GoodsInfo>>.Resp> getGoodsInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<GoodsInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(goodsInfoDao.getAllGoodsInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(goodsInfoDao.getGoodsInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除GoodsInfo", notes = "软删除主键为id的GoodsInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "GoodsInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delGoodsInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(id);
        goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = goodsInfoDao.updateGoodsInfo(goodsInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
