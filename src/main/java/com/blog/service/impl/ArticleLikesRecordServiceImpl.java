package com.blog.service.impl;

import com.blog.mapper.ArticleLikesMapper;
import com.blog.model.ArticleLikesRecord;
import com.blog.service.ArticleLikesRecordService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/7/7 15:50
 * Describe:
 */
@Service
public class ArticleLikesRecordServiceImpl implements ArticleLikesRecordService {

    @Autowired
    ArticleLikesMapper articleLikesMapper;
    @Autowired
    UserService userService;

    @Override
    public boolean isLiked(long articleId, String originalAuthor, String username) {
        ArticleLikesRecord articleLikesRecord = articleLikesMapper.isLiked(articleId, originalAuthor, userService.findIdByUsername(username));

        return articleLikesRecord != null;
    }

    @Override
    public void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        articleLikesMapper.insertArticleLikesRecord(articleLikesRecord);
    }

    @Override
    public void deleteArticleLikesRecordByArticleId(long articleId) {
        articleLikesMapper.deleteArticleLikesRecordByArticleId(articleId);
    }

}
