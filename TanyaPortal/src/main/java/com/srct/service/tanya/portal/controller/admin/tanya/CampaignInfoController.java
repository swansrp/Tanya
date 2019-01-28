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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.CampaignInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "CampaignInfo")
@RestController("tanyaCampaignInfoController")
@RequestMapping(value = "/portal/admin/tanya/campaigninfo")
@CrossOrigin(origins = "*")
public class CampaignInfoController {

    @Autowired
    CampaignInfoDao campaignInfoDao;

    @ApiOperation(value = "更新CampaignInfo", notes = "传入CampaignInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "CampaignInfoEntityVO", name = "vo", value = "CampaignInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateCampaignInfo(@RequestBody CampaignInfoEntityVO vo) {
        CampaignInfo campaignInfo = new CampaignInfo();
        BeanUtil.copyProperties(vo, campaignInfo);
        Integer id = campaignInfoDao.updateCampaignInfo(campaignInfo);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询CampaignInfo", notes = "传入CampaignInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "CampaignInfoEntityVO", name = "vo", value = "CampaignInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<CampaignInfo>>.Resp> getCampaignInfoSelective(
            @RequestBody CampaignInfoEntityVO vo
            ) {
        List<CampaignInfo> res = new ArrayList<>();
        CampaignInfo campaignInfo = new CampaignInfo();
        BeanUtil.copyProperties(vo, campaignInfo);
        res.addAll(campaignInfoDao.getCampaignInfoSelective(campaignInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询CampaignInfo", notes = "返回id对应的CampaignInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<CampaignInfo>>.Resp> getCampaignInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<CampaignInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(campaignInfoDao.getAllCampaignInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(campaignInfoDao.getCampaignInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除CampaignInfo", notes = "软删除主键为id的CampaignInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delCampaignInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        CampaignInfo campaignInfo = new CampaignInfo();
        campaignInfo.setId(id);
        campaignInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = campaignInfoDao.updateCampaignInfo(campaignInfo);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
