/**
 * Title: CampaignHistoryController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-03-04 20:24:34
 */
package com.srct.service.tanya.product.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignHistoryService;
import com.srct.service.tanya.product.vo.CampaignHistoryReqVO;
import com.srct.service.tanya.product.vo.CampaignHistoryRespVO;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sharuopeng
 *
 */

@Api(value = "促销进展")
@RestController("CampaignHistoryController")
@RequestMapping(value = "/campaign/history")
@CrossOrigin(origins = "*")
public class CampaignHistoryController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CampaignHistoryService campaignHistoryService;

    @ApiOperation(value = "上报促销进展", notes = "只有salesman等级可以上报促销进展 若传入id则为更新")
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignHistoryRespVO>>.Resp>
        report(@RequestBody CampaignHistoryReqVO req) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***reportCampaignHistory***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignHistoryReqVO> history = new ProductBO<CampaignHistoryReqVO>();
        history.setReq(req);
        history.setCreaterInfo(info);
        history.setCreaterRole(role);
        QueryRespVO<CampaignHistoryRespVO> campaignHistoryVOList =
            campaignHistoryService.updateCampaignHistory(history);

        return TanyaExceptionHandler.generateResponse(campaignHistoryVOList);
    }

    @ApiOperation(value = "获取促销活动", notes = "获取促销活动详情,无id则返回渠道促销活动列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求", required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "salesmanid", value = "促销员id",
            required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "上报进展id", required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignHistoryRespVO>>.Resp> getCampaign(
        @RequestBody QueryReqVO req,
        @RequestParam(value = "salesmanid", required = false) Integer salesmanId,
        @RequestParam(value = "id", required = false) Integer campaignHistoryId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***getCampaignHistory***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> history = new ProductBO<QueryReqVO>();
        history.setReq(req);
        history.setCreaterInfo(info);
        history.setCreaterRole(role);
        history.setProductId(campaignHistoryId);
        history.setSalesmanId(salesmanId);
        QueryRespVO<CampaignHistoryRespVO> campaignHistoryVOList = campaignHistoryService.getCampaignHistory(history);

        return TanyaExceptionHandler.generateResponse(campaignHistoryVOList);
    }

    @ApiOperation(value = "审批促销进展", notes = "审批促销活动  同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "上报进展id", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "boolean", name = "confirmed", value = "true/false",
            required = true),
        @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "rewards", value = "该笔促销累计积分",
            required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignHistoryRespVO>>.Resp> confirm(
        @RequestParam(value = "id", required = true) Integer id,
        @RequestParam(value = "confirmed", required = true) boolean confirmed,
        @RequestParam(value = "rewards", required = false) Integer rewards) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***confirmHistory***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> history = new ProductBO<QueryReqVO>();
        history.setProductType("campaignHistory");
        history.setCreaterInfo(info);
        history.setCreaterRole(role);
        history.setProductId(id);
        history.setApproved(confirmed);
        QueryRespVO<CampaignHistoryRespVO> campaignHistoryVO =
            campaignHistoryService.confirmCampaignHistory(history, rewards);

        return TanyaExceptionHandler.generateResponse(campaignHistoryVO);
    }

    @ApiOperation(value = "删除促销报告", notes = "只有salesman等级可以删除促销报告,已经确认的不能删除")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Interger", name = "campaignHistoryid",
        value = "促销活动id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignHistoryRespVO>>.Resp>
        del(@RequestParam(value = "campaignHistoryid", required = true) Integer campaignHistoryId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***DelCampaignHistory***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignHistoryReqVO> campaignHistory = new ProductBO<CampaignHistoryReqVO>();
        campaignHistory.setCreaterInfo(info);
        campaignHistory.setCreaterRole(role);
        campaignHistory.setProductId(campaignHistoryId);
        QueryRespVO<CampaignHistoryRespVO> goodsInfoVOList = campaignHistoryService.delCampaignHistory(campaignHistory);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

}
