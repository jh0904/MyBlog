package com.blog.model;

import lombok.Data;

/**
 * @author: i_jianghao
 * @Date: 2018/6/16 16:03
 * Describe: 访客
 */
@Data
public class Visitor {

    private int id;

    /**
     * 访客人数
     */
    private long visitorNum;

    /**
     * 当前页的name or 文章名
     */
    private String pageName;

    /**
     * 文章id
     */
    private long articleId;
}
