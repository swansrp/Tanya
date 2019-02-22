/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: sharuopeng   
 * @date: 2019/02/23
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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.TraderInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "TraderInfo")
@RestController("tanyaTraderInfoController")
@RequestMapping(value = "/portal/admin/tanya/traderinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class TraderInfoController {

    @Autowired
    TraderInfoDao traderInfoDao;

    @ApiOperation(value = "更新TraderInfo", notes = "传入TraderInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "TraderInfoEntityVO", name = "vo", value = "TraderInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateTraderInfo(@RequestBody TraderInfoEntityVO vo) {
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(vo, traderInfo);
        Integer id = traderInfoDao.updateTraderInfo(traderInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询TraderInfo", notes = "传入TraderInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "TraderInfoEntityVO", name = "vo", value = "TraderInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<TraderInfo>>.Resp> getTraderInfoSelective(
            @RequestBody TraderInfoEntityVO vo
            ) {
        List<TraderInfo> res = new ArrayList<>();
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(vo, traderInfo);
        res.addAll(traderInfoDao.getTraderInfoSelective(traderInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询TraderInfo", notes = "返回id对应的TraderInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "TraderInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<TraderInfo>>.Resp> getTraderInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<TraderInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(traderInfoDao.getAllTraderInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(traderInfoDao.getTraderInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除TraderInfo", notes = "软删除主键为id的TraderInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "TraderInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delTraderInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        TraderInfo traderInfo = new TraderInfo();
        traderInfo.setId(id);
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = traderInfoDao.updateTraderInfo(traderInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
