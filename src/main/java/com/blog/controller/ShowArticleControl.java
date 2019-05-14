package com.blog.controller;

import com.blog.model.ArticleLikesRecord;
import com.blog.service.ArticleLikesRecordService;
import com.blog.service.ArticleService;
import com.blog.service.UserService;
import com.blog.utils.TimeUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author: i_jianghao
 * @Date: 2018/7/5 16:21
 * Describe: 文章显示页面
 */
@Controller
public class ShowArticleControl {

    private Logger logger = LoggerFactory.getLogger(ShowArticleControl.class);

    @Autowired
    ArticleLikesRecordService articleLikesRecordService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    /**
     *  获取文章
     * @param articleId 文章id
     * @return
     */
    @PostMapping("/getArticleByArticleIdAndOriginalAuthor")
    public @ResponseBody JSONObject getArticleByIdAndOriginalAuthor(@RequestParam("articleId") String articleId,
                                                                    @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            logger.info("This user is not login");
        }
        return articleService.getArticleByArticleIdAndOriginalAuthor(Long.parseLong(articleId),username);
    }




    /**
     * 点赞
     * @param articleId 文章号
     * @return
     */
    @GetMapping("/addArticleLike")
    public @ResponseBody int addArticleLike(@RequestParam("articleId") String articleId,
                                     @RequestParam("originalAuthor") String originalAuthor,
                                     @AuthenticationPrincipal Principal principal){

        String username="";
        try {
            username = principal.getName();
        }catch (NullPointerException e){
            logger.error("username " + username + " is not login");
            return -1;
        }

        //String stringOriginalAuthor = TransCodingUtil.unicodeToString(originalAuthor);
        if(articleLikesRecordService.isLiked(Long.parseLong(articleId), username)){
            logger.info("你已经点过赞了");
            return -2;
        }
        int likes = articleService.updateLikeByArticleIdAndOriginalAuthor(Long.parseLong(articleId));
        ArticleLikesRecord articleLikesRecord = new ArticleLikesRecord(Long.parseLong(articleId), "", userService.findIdByUsername(username), new TimeUtil().getFormatDateForFive());
        articleLikesRecordService.insertArticleLikesRecord(articleLikesRecord);
        logger.info("点赞成功");
        return likes;
    }

}
