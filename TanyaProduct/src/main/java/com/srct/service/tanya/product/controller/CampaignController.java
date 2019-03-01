/**
 * Title: CampaignController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 20:43:39
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
import com.srct.service.tanya.product.service.CampaignService;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sharuopeng
 *
 */
@Api(value = "促销活动(药店促销员)")
@RestController("CampaignController")
@RequestMapping(value = "/campaign")
@CrossOrigin(origins = "*")
public class CampaignController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CampaignService campaignService;

    @ApiOperation(value = "新增/更新促销活动", notes = "只有trader等级可以添加促销活动 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp>
        modifyCampaign(@RequestBody CampaignInfoReqVO vo) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignInfoReqVO> campaign = new ProductBO<CampaignInfoReqVO>();
        BeanUtil.copyProperties(vo, campaign);
        campaign.setCreaterInfo(info);
        campaign.setCreaterRole(role);
        QueryRespVO<CampaignInfoRespVO> goodsInfoVOList = campaignService.updateCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取促销活动", notes = "获取促销活动详情,无id则返回渠道促销活动列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求", required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "campaignid", value = "促销活动id",
            required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> getCampaign(
        @RequestBody QueryReqVO req,
        @RequestParam(value = "campaignid", required = false) Integer campaignId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***getOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> campaign = new ProductBO<QueryReqVO>();
        campaign.setCreaterInfo(info);
        campaign.setCreaterRole(role);
        campaign.setProductId(campaignId);
        QueryRespVO<CampaignInfoRespVO> goodsInfoVOList = campaignService.getCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "审批促销活动", notes = "审批促销活动  同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "campaignid", value = "订单id",
            required = true),
        @ApiImplicitParam(paramType = "query", dataType = "boolean", name = "confirmed", value = "true/false",
            required = true)})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> confirm(
        @RequestParam(value = "campaignid", required = true) Integer campaignId,
        @RequestParam(value = "confirmed", required = true) boolean confirmed) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***confirmCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> campaign = new ProductBO<QueryReqVO>();
        campaign.setProductType("campaign");
        campaign.setCreaterInfo(info);
        campaign.setCreaterRole(role);
        campaign.setProductId(campaignId);
        campaign.setApproved(confirmed);
        QueryRespVO<CampaignInfoRespVO> campaignInfoVO = campaignService.confirmCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(campaignInfoVO);
    }

}
