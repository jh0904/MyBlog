package com.blog.model;

import lombok.Data;

/**
 * @author: i_jianghao
 * @Date: 2018/6/20 15:34
 * Describe: 文章
 */
@Data
public class Article {

    private static final long serialVersionUID = 1L;

    //@JestId
    private int id;

    /**
     * 文章id
     */
    private long articleId;

    /**
     * 用户id
     */
    private int user_id;

    /**
     * 文章名
     */
    private String articleTitle;

    /**
     * 发布时间
     */
    private String publishDate;

    /**
     * 最后一次修改时间
     */
    private String updateDate;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章标签
     */
    private String articleTags;

    /**
     * 文章类型
     */
    private String articleType;

    /**
     * 博客分类
     */
    private String articleCategories;


    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    private String articleUrl;

    /**
     * 文章摘要
     */
    private String articleTabloid;

    /**
     * 上一篇文章id
     */
    private long lastArticleId;

    /**
     * 下一篇文章id
     */
    private long nextArticleId;

    /**
     * 喜欢
     */
    private int likes = 0;

}
