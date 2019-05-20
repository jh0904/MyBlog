package com.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: i_jianghao
 * @Date: 2018/6/16 16:22
 * Describe: 访客sql
 */
@Mapper
@Repository
public interface VisitorMapper {

    @Select("select visitorNum from visitor where pageName=#{pageName}")
    long getVisitorNumByPageName(@Param("pageName") String pageName);

    @Update("update visitor set visitorNum=(case pageName when 'totalVisitor' then visitorNum+1 when #{pageName} then visitorNum+1 else visitorNum end)")
    void updateVisitorNumByTotalVisitorAndPageName(@Param("pageName") String pageName);

    @Update("update visitor set visitorNum=visitorNum+1 where pageName='totalVisitor'")
    void updateVisitorNumByTotalVisitor();

    @Insert("insert into visitor(visitorNum,pageName,articleId) values(0,#{pageName},#{articleId})")
    void insertVisitorArticlePage(@Param("pageName")String pageName,@Param("articleId")long articleId);

    @Select("select visitorNum from visitor where pageName='totalVisitor'")
    long getAllVisitor();

    @Delete ("DELETE FROM visitor WHERE articleId=#{articleId}")
    void deleteVisitorByArticleId(@Param ("articleId") long articleId);
}
