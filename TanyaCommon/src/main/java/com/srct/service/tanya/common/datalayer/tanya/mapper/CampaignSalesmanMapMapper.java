package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMapExample;

@Component("tanyaCampaignSalesmanMapMapper")
public interface CampaignSalesmanMapMapper {

    long countByExample(CampaignSalesmanMapExample example);

    int deleteByExample(CampaignSalesmanMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CampaignSalesmanMap record);

    int insertSelective(CampaignSalesmanMap record);

    List<CampaignSalesmanMap> selectByExample(CampaignSalesmanMapExample example);

    CampaignSalesmanMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CampaignSalesmanMap record, @Param("example") CampaignSalesmanMapExample example);

    int updateByExample(@Param("record") CampaignSalesmanMap record, @Param("example") CampaignSalesmanMapExample example);

    int updateByPrimaryKeySelective(CampaignSalesmanMap record);

    int updateByPrimaryKey(CampaignSalesmanMap record);
}