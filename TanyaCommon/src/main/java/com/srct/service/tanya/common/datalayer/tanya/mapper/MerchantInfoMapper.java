package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfoExample;

@Component("tanyaMerchantInfoMapper")
public interface MerchantInfoMapper {

    long countByExample(MerchantInfoExample example);

    int deleteByExample(MerchantInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchantInfo record);

    int insertSelective(MerchantInfo record);

    List<MerchantInfo> selectByExample(MerchantInfoExample example);

    MerchantInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantInfo record, @Param("example") MerchantInfoExample example);

    int updateByExample(@Param("record") MerchantInfo record, @Param("example") MerchantInfoExample example);

    int updateByPrimaryKeySelective(MerchantInfo record);

    int updateByPrimaryKey(MerchantInfo record);
}