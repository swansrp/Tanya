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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.SalesmanInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "SalesmanInfo")
@RestController("tanyaSalesmanInfoController")
@RequestMapping(value = "/portal/admin/tanya/salesmaninfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class SalesmanInfoController {

    @Autowired
    SalesmanInfoDao salesmanInfoDao;

    @ApiOperation(value = "更新SalesmanInfo", notes = "传入SalesmanInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "SalesmanInfoEntityVO", name = "vo", value = "SalesmanInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateSalesmanInfo(@RequestBody SalesmanInfoEntityVO vo) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(vo, salesmanInfo);
        Integer id = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询SalesmanInfo", notes = "传入SalesmanInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "SalesmanInfoEntityVO", name = "vo", value = "SalesmanInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<SalesmanInfo>>.Resp> getSalesmanInfoSelective(
            @RequestBody SalesmanInfoEntityVO vo
            ) {
        List<SalesmanInfo> res = new ArrayList<>();
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(vo, salesmanInfo);
        res.addAll(salesmanInfoDao.getSalesmanInfoSelective(salesmanInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询SalesmanInfo", notes = "返回id对应的SalesmanInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "SalesmanInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<SalesmanInfo>>.Resp> getSalesmanInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<SalesmanInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(salesmanInfoDao.getAllSalesmanInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(salesmanInfoDao.getSalesmanInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除SalesmanInfo", notes = "软删除主键为id的SalesmanInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "SalesmanInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delSalesmanInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        salesmanInfo.setId(id);
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
