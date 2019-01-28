package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfoExample;

@Component("tanyaCampaignInfoMapper")
public interface CampaignInfoMapper {

    long countByExample(CampaignInfoExample example);

    int deleteByExample(CampaignInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CampaignInfo record);

    int insertSelective(CampaignInfo record);

    List<CampaignInfo> selectByExample(CampaignInfoExample example);

    CampaignInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CampaignInfo record, @Param("example") CampaignInfoExample example);

    int updateByExample(@Param("record") CampaignInfo record, @Param("example") CampaignInfoExample example);

    int updateByPrimaryKeySelective(CampaignInfo record);

    int updateByPrimaryKey(CampaignInfo record);
}