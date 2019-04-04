package com.srct.service.tanya.common.datalayer.tanya.mapper;

import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantAdminMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantAdminMapExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tanyaMerchantAdminMapMapper")
public interface MerchantAdminMapMapper {

    long countByExample(MerchantAdminMapExample example);

    int deleteByExample(MerchantAdminMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchantAdminMap record);

    int insertSelective(MerchantAdminMap record);

    List<MerchantAdminMap> selectByExample(MerchantAdminMapExample example);

    MerchantAdminMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantAdminMap record,
            @Param("example") MerchantAdminMapExample example);

    int updateByExample(@Param("record") MerchantAdminMap record, @Param("example") MerchantAdminMapExample example);

    int updateByPrimaryKeySelective(MerchantAdminMap record);

    int updateByPrimaryKey(MerchantAdminMap record);
}
