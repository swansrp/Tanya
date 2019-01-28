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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignHistoryDao;
import com.srct.service.tanya.portal.vo.admin.tanya.CampaignHistoryEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "CampaignHistory")
@RestController("tanyaCampaignHistoryController")
@RequestMapping(value = "/portal/admin/tanya/campaignhistory")
@CrossOrigin(origins = "*")
public class CampaignHistoryController {

    @Autowired
    CampaignHistoryDao campaignHistoryDao;

    @ApiOperation(value = "更新CampaignHistory", notes = "传入CampaignHistory值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "CampaignHistoryEntityVO", name = "vo", value = "CampaignHistory", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateCampaignHistory(@RequestBody CampaignHistoryEntityVO vo) {
        CampaignHistory campaignHistory = new CampaignHistory();
        BeanUtil.copyProperties(vo, campaignHistory);
        Integer id = campaignHistoryDao.updateCampaignHistory(campaignHistory);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询CampaignHistory", notes = "传入CampaignHistory值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "CampaignHistoryEntityVO", name = "vo", value = "CampaignHistory", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<CampaignHistory>>.Resp> getCampaignHistorySelective(
            @RequestBody CampaignHistoryEntityVO vo
            ) {
        List<CampaignHistory> res = new ArrayList<>();
        CampaignHistory campaignHistory = new CampaignHistory();
        BeanUtil.copyProperties(vo, campaignHistory);
        res.addAll(campaignHistoryDao.getCampaignHistorySelective(campaignHistory));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询CampaignHistory", notes = "返回id对应的CampaignHistory,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignHistory的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<CampaignHistory>>.Resp> getCampaignHistory(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<CampaignHistory> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(campaignHistoryDao.getAllCampaignHistoryList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(campaignHistoryDao.getCampaignHistorybyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除CampaignHistory", notes = "软删除主键为id的CampaignHistory")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignHistory的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delCampaignHistory(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        CampaignHistory campaignHistory = new CampaignHistory();
        campaignHistory.setId(id);
        campaignHistory.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = campaignHistoryDao.updateCampaignHistory(campaignHistory);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
