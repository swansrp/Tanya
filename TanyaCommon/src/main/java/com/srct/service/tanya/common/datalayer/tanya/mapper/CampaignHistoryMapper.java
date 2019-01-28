package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;

@Component("tanyaCampaignHistoryMapper")
public interface CampaignHistoryMapper {

    long countByExample(CampaignHistoryExample example);

    int deleteByExample(CampaignHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CampaignHistory record);

    int insertSelective(CampaignHistory record);

    List<CampaignHistory> selectByExample(CampaignHistoryExample example);

    CampaignHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CampaignHistory record, @Param("example") CampaignHistoryExample example);

    int updateByExample(@Param("record") CampaignHistory record, @Param("example") CampaignHistoryExample example);

    int updateByPrimaryKeySelective(CampaignHistory record);

    int updateByPrimaryKey(CampaignHistory record);
}