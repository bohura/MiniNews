package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Headline;
import com.example.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * テーブル【news_headline】に対するデータベース操作Mapper
 *
 * @Entity com.example.pojo.Headline
 */
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage<Map> page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}
