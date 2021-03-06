package com.blog.service;

import com.blog.model.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: i_jianghao
 * @Date: 2018/7/6 16:46
 * Describe:评论业务操作
 */
public interface CommentService {

    /**
     * 保存留言
     * @param comment 留言
     */
    @Transactional
    Comment insertComment(Comment comment);

    /**
     * 通过文章id和原作者获得文章所有评论和回复
     * @param articleId 文章id
     * @return
     */
    JSONArray findCommentByArticleIdAndOriginalAuthor(long articleId, String username);

    /**
     * 通过文章id、原作者和pId获得评论下的所有回复
     * @param articleId
     * @param pId 评论的id
     * @return
     */
    JSONArray findReplyByArticleIdAndOriginalAuthorAndPid(long articleId, long pId);

    /**
     * 返回评论中的回复
     * @param comment
     * @return
     */
    JSONArray replyReplyReturn(Comment comment, String answerer, String respondent);

    /**
     * 更新评论点赞数
     * @param articleId 文章id
     * @param pId 该评论的id
     * @return 点赞数
     */
    int updateLikeByArticleIdAndOriginalAuthorAndId(long articleId, long pId);

    /**
     * 获得最新的5条评论
     * @return
     */
    JSONObject findFiveNewComment(int rows, int pageNum);

    /**
     * 分页获得用户所有的评论
     * @param rows 一页大小
     * @param pageNum 当前页
     * @param username 用户
     * @return
     */
    JSONObject getUserComment(int rows, int pageNum, String username);

    /**
     * 获得评论总数
     */
    int commentNum();

    /**
     * 通过文章id删除该文章的所有评论
     * @param articleId 文章id'
     */
    void deleteCommentByArticleId(long articleId);

}
