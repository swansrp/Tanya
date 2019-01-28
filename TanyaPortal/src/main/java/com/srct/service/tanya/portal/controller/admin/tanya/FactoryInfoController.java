/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.FactoryInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "FactoryInfo")
@RestController("tanyaFactoryInfoController")
@RequestMapping(value = "/portal/admin/tanya/factoryinfo")
@CrossOrigin(origins = "*")
public class FactoryInfoController {

    @Autowired
    FactoryInfoDao factoryInfoDao;

    @ApiOperation(value = "更新FactoryInfo", notes = "传入FactoryInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "FactoryInfoEntityVO", name = "vo", value = "FactoryInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateFactoryInfo(@RequestBody FactoryInfoEntityVO vo) {
        FactoryInfo factoryInfo = new FactoryInfo();
        BeanUtil.copyProperties(vo, factoryInfo);
        Integer id = factoryInfoDao.updateFactoryInfo(factoryInfo);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询FactoryInfo", notes = "传入FactoryInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "FactoryInfoEntityVO", name = "vo", value = "FactoryInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<FactoryInfo>>.Resp> getFactoryInfoSelective(
            @RequestBody FactoryInfoEntityVO vo
            ) {
        List<FactoryInfo> res = new ArrayList<>();
        FactoryInfo factoryInfo = new FactoryInfo();
        BeanUtil.copyProperties(vo, factoryInfo);
        res.addAll(factoryInfoDao.getFactoryInfoSelective(factoryInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询FactoryInfo", notes = "返回id对应的FactoryInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "FactoryInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<FactoryInfo>>.Resp> getFactoryInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<FactoryInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(factoryInfoDao.getAllFactoryInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(factoryInfoDao.getFactoryInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除FactoryInfo", notes = "软删除主键为id的FactoryInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "FactoryInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delFactoryInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        FactoryInfo factoryInfo = new FactoryInfo();
        factoryInfo.setId(id);
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = factoryInfoDao.updateFactoryInfo(factoryInfo);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
