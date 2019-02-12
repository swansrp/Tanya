/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/02/12
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
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.DiscountInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.DiscountInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "DiscountInfo")
@RestController("tanyaDiscountInfoController")
@RequestMapping(value = "/portal/admin/tanya/discountinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class DiscountInfoController {

    @Autowired
    DiscountInfoDao discountInfoDao;

    @ApiOperation(value = "更新DiscountInfo", notes = "传入DiscountInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "DiscountInfoEntityVO", name = "vo", value = "DiscountInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateDiscountInfo(@RequestBody DiscountInfoEntityVO vo) {
        DiscountInfo discountInfo = new DiscountInfo();
        BeanUtil.copyProperties(vo, discountInfo);
        Integer id = discountInfoDao.updateDiscountInfo(discountInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询DiscountInfo", notes = "传入DiscountInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "DiscountInfoEntityVO", name = "vo", value = "DiscountInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<DiscountInfo>>.Resp> getDiscountInfoSelective(
            @RequestBody DiscountInfoEntityVO vo
            ) {
        List<DiscountInfo> res = new ArrayList<>();
        DiscountInfo discountInfo = new DiscountInfo();
        BeanUtil.copyProperties(vo, discountInfo);
        res.addAll(discountInfoDao.getDiscountInfoSelective(discountInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询DiscountInfo", notes = "返回id对应的DiscountInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "DiscountInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<DiscountInfo>>.Resp> getDiscountInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<DiscountInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(discountInfoDao.getAllDiscountInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(discountInfoDao.getDiscountInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除DiscountInfo", notes = "软删除主键为id的DiscountInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "DiscountInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delDiscountInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setId(id);
        discountInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = discountInfoDao.updateDiscountInfo(discountInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
