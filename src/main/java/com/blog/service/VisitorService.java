package com.blog.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: i_jianghao
 * @Date: 2018/6/16 16:19
 * Describe:访客业务操作
 */
public interface VisitorService {

    /**
     * 通过页名增加访客量
     * @param pageName
     */
    void addVisitorNumByPageName(String pageName, HttpServletRequest request);

    /**
     * 通过页名获得总访问量和访客量
     * @param pageName 页名
     * @return
     */
    JSONObject getVisitorNumByPageName(String pageName);

    /**
     * 通过页名获得访客量
     * @param pageName 页名
     * @return 访客量
     */
    long getNumByPageName(String pageName);

    /**
     * 发布文章后保存该文章的访客量
     * @param articleId 文章id
     */
    void insertVisitorArticlePage(long articleId);

    /**
     * 获得总访问量
     * @return
     */
    long getAllVisitor();

    /**
     * 通过文章id删除该文章的所有评论
     * @param articleId 文章id'
     */
    void deleteVisitorByArticleId(long articleId);
}
