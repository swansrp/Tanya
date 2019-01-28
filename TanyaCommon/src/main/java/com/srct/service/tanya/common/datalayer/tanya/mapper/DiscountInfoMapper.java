package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfoExample;

@Component("tanyaDiscountInfoMapper")
public interface DiscountInfoMapper {

    long countByExample(DiscountInfoExample example);

    int deleteByExample(DiscountInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DiscountInfo record);

    int insertSelective(DiscountInfo record);

    List<DiscountInfo> selectByExample(DiscountInfoExample example);

    DiscountInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DiscountInfo record, @Param("example") DiscountInfoExample example);

    int updateByExample(@Param("record") DiscountInfo record, @Param("example") DiscountInfoExample example);

    int updateByPrimaryKeySelective(DiscountInfo record);

    int updateByPrimaryKey(DiscountInfo record);
}