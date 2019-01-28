package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfoExample;

@Component("tanyaTraderInfoMapper")
public interface TraderInfoMapper {

    long countByExample(TraderInfoExample example);

    int deleteByExample(TraderInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TraderInfo record);

    int insertSelective(TraderInfo record);

    List<TraderInfo> selectByExample(TraderInfoExample example);

    TraderInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TraderInfo record, @Param("example") TraderInfoExample example);

    int updateByExample(@Param("record") TraderInfo record, @Param("example") TraderInfoExample example);

    int updateByPrimaryKeySelective(TraderInfo record);

    int updateByPrimaryKey(TraderInfo record);
}