package com.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: i_jianghao
 * @Date: 2018/7/17 20:54
 * Describe: 分类sql
 */
@Mapper
@Repository
public interface CategoryMapper {

    @Select("select categoryName from categories")
    List<String> findCategoriesName();

    @Select("SELECT tagName FROM tags;")
    List<String> findTagsName();

    @Select("select count(*) from categories")
    int countCategoriesNum();

}
