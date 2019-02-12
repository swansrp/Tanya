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
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.MerchantInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "MerchantInfo")
@RestController("tanyaMerchantInfoController")
@RequestMapping(value = "/portal/admin/tanya/merchantinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class MerchantInfoController {

    @Autowired
    MerchantInfoDao merchantInfoDao;

    @ApiOperation(value = "更新MerchantInfo", notes = "传入MerchantInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "MerchantInfoEntityVO", name = "vo", value = "MerchantInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateMerchantInfo(@RequestBody MerchantInfoEntityVO vo) {
        MerchantInfo merchantInfo = new MerchantInfo();
        BeanUtil.copyProperties(vo, merchantInfo);
        Integer id = merchantInfoDao.updateMerchantInfo(merchantInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询MerchantInfo", notes = "传入MerchantInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "MerchantInfoEntityVO", name = "vo", value = "MerchantInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<MerchantInfo>>.Resp> getMerchantInfoSelective(
            @RequestBody MerchantInfoEntityVO vo
            ) {
        List<MerchantInfo> res = new ArrayList<>();
        MerchantInfo merchantInfo = new MerchantInfo();
        BeanUtil.copyProperties(vo, merchantInfo);
        res.addAll(merchantInfoDao.getMerchantInfoSelective(merchantInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询MerchantInfo", notes = "返回id对应的MerchantInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "MerchantInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<MerchantInfo>>.Resp> getMerchantInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<MerchantInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(merchantInfoDao.getAllMerchantInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(merchantInfoDao.getMerchantInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除MerchantInfo", notes = "软删除主键为id的MerchantInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "MerchantInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delMerchantInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setId(id);
        merchantInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = merchantInfoDao.updateMerchantInfo(merchantInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
