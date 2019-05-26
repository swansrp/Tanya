/**
 * Title: CampaignController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 20:43:39
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignService;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author sharuopeng
 */
@Auth(role = USER)
@Api(value = "促销活动(药店促销员)", tags = "促销活动(药店促销员)")
@RestController("CampaignController")
@RequestMapping(value = "/campaign")
@CrossOrigin(origins = "*")
public class CampaignController {

    private final static String productType = "促销活动";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CampaignService campaignService;

    @ApiOperation(value = "新增/更新促销活动", notes = "只有trader等级可以添加促销活动 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> modifyCampaign(
            @RequestBody CampaignInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignInfoReqVO> campaign = new ProductBO<>();
        campaign.setProductType(productType);
        campaign.setReq(req);
        campaign.setCreatorInfo(info);
        campaign.setCreatorRole(role);
        QueryRespVO<CampaignInfoRespVO> goodsInfoVOList = campaignService.updateCampaignInfo(campaign);
        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "绑定促销活动", notes = "只有trader等级可以绑定促销活动")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> bindCampaign(
            @RequestBody CampaignInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***bindCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignInfoReqVO> campaign = new ProductBO<>();
        campaign.setProductType(productType);
        campaign.setReq(req);
        campaign.setCreatorInfo(info);
        campaign.setCreatorRole(role);
        QueryRespVO<CampaignInfoRespVO> goodsInfoVOList = null;
        if (req.getUnbindSalesmanIdList() != null || req.getBindSalesmanIdList() != null) {
            goodsInfoVOList = campaignService.bindCampaignInfoSalesman(campaign);
        }
        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取促销活动", notes = "获取促销活动详情,无id则返回渠道促销活动列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "campaignid", value = "促销活动id"),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意 null全部 -1未操作")})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> getCampaign(@RequestBody QueryReqVO req,
            @RequestParam(value = "campaignid", required = false) Integer campaignId,
            @RequestParam(value = "confirmed", required = false) Byte confirmed) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> campaign = new ProductBO<>();
        campaign.setProductType(productType);
        campaign.setReq(req);
        campaign.setCreatorInfo(info);
        campaign.setCreatorRole(role);
        campaign.setProductId(campaignId);
        campaign.setApproved(confirmed);
        QueryRespVO<CampaignInfoRespVO> campaignInfoVOList = campaignService.getCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(campaignInfoVOList);
    }

    @ApiOperation(value = "审批促销活动", notes = "审批促销活动  同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "campaignid", value = "订单id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意", required = true)})
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> confirm(
            @RequestParam(value = "campaignid") Integer campaignId, @RequestParam(value = "confirmed") Byte confirmed) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***confirmCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> campaign = new ProductBO<>();
        campaign.setProductType(productType);
        campaign.setCreatorInfo(info);
        campaign.setCreatorRole(role);
        campaign.setProductId(campaignId);
        campaign.setApproved(confirmed);
        QueryRespVO<CampaignInfoRespVO> campaignInfoVO = campaignService.confirmCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(campaignInfoVO);
    }

    @ApiOperation(value = "删除促销活动", notes = "只有trader等级可以删除促销活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "campaignid", value = "促销活动id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignInfoRespVO>>.Resp> del(
            @RequestParam(value = "campaignid") Integer campaignId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelCampaign***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<CampaignInfoReqVO> campaign = new ProductBO<>();
        campaign.setProductType(productType);
        campaign.setCreatorInfo(info);
        campaign.setCreatorRole(role);
        campaign.setProductId(campaignId);
        QueryRespVO<CampaignInfoRespVO> goodsInfoVOList = campaignService.delCampaignInfo(campaign);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

}
