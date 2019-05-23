package com.blog.controller;

import com.blog.component.JavaScriptCheck;
import com.blog.constant.SiteOwner;
import com.blog.model.Comment;
import com.blog.model.CommentLikesRecord;
import com.blog.service.CommentLikesRecordService;
import com.blog.service.CommentService;
import com.blog.service.UserService;
import com.blog.utils.TimeUtil;
import net.sf.json.JSONArray;
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
 * @Date: 2018/7/5 23:14
 * Describe: 评论和回复
 */
@Controller
public class CommentControl {

	private Logger logger = LoggerFactory.getLogger (ShowArticleControl.class);

	@Autowired
	private CommentService commentService;
	@Autowired
	CommentLikesRecordService commentLikesRecordService;
	@Autowired
	UserService userService;

	/**
	 * 获得该文章所有评论
	 *
	 * @param articleId 文章id
	 * @return
	 */
	@PostMapping("/getAllComment")
	@ResponseBody
	public JSONArray getAllComment(@RequestParam("articleId") String articleId,
	                               @AuthenticationPrincipal Principal principal) {

		String username = null;
		try {
			username = principal.getName ();
		} catch (NullPointerException e) {
			logger.info ("This user is not login");
		}
		JSONArray jsonArray = commentService.findCommentByArticleIdAndOriginalAuthor (Long.parseLong (articleId), username);
		return jsonArray;

	}

	/**
	 * 评论
	 *
	 * @param principal 当前用户
	 * @return
	 */
	@PostMapping("/publishComment")
	@ResponseBody
	public JSONArray publishComment(Comment comment,
	                                @AuthenticationPrincipal Principal principal) {
		String publisher;
		try {
			publisher = principal.getName ();
		} catch (NullPointerException e) {
			logger.error ("no principal,please to login");
			JSONObject jsonObject = new JSONObject ();
			jsonObject.put ("status", 403);
			JSONArray jsonArray = new JSONArray ();
			jsonArray.add (jsonObject);
			return jsonArray;
		}
		TimeUtil timeUtil = new TimeUtil ();
		comment.setCommentDate (timeUtil.getFormatDateForFive ());
		int userId = userService.findIdByUsername (publisher);
		comment.setAnswererId (userId);
		comment.setRespondentId (userService.findIdByUsername (SiteOwner.SITE_OWNER));
		comment.setCommentContent (JavaScriptCheck.javaScriptCheck (comment.getCommentContent ()));

		commentService.insertComment (comment);
		JSONArray jsonArray = commentService.findCommentByArticleIdAndOriginalAuthor (comment.getArticleId (), publisher);
		return jsonArray;
	}

	/**
	 * 评论中的回复
	 *
	 * @param principal 当前用户
	 * @return
	 */
	@PostMapping("/publishReply")
	@ResponseBody
	public JSONArray publishReply(Comment comment,
	                              @RequestParam("parentId") String parentId,
	                              @RequestParam("respondent") String respondent,
	                              @AuthenticationPrincipal Principal principal) {

		String username = null;
		try {
			username = principal.getName ();
		} catch (NullPointerException e) {
			JSONArray jsonArray = new JSONArray ();
			JSONObject jsonObject = new JSONObject ();
			jsonObject.put ("status", 403);
			jsonArray.add (jsonObject);
			return jsonArray;
		}

		comment.setPId (Long.parseLong (parentId.substring (1)));
		comment.setAnswererId (userService.findIdByUsername (username));
		comment.setRespondentId (userService.findIdByUsername (respondent));
		TimeUtil timeUtil = new TimeUtil ();
		comment.setCommentDate (timeUtil.getFormatDateForFive ());
		comment.setCommentContent (JavaScriptCheck.javaScriptCheck (comment.getCommentContent ()));
		comment = commentService.insertComment (comment);
		JSONArray jsonArray = commentService.replyReplyReturn (comment, username, respondent);
		return jsonArray;
	}

	/**
	 * 是否登陆
	 *
	 * @param principal 当前用户
	 * @return
	 */
	@GetMapping("/isLogin")
	@ResponseBody
	public int isLogin(@AuthenticationPrincipal Principal principal) {
		String username;
		try {
			username = principal.getName ();
			return 1;
		} catch (NullPointerException e) {
			logger.info ("This user is not login");
			return 0;
		}
	}

	/**
	 * 评论点赞
	 *
	 * @param articleId    文章id
	 * @param respondentId 评论的id
	 * @param principal    当前用户
	 * @return 点赞数
	 */
	@GetMapping("/addCommentLike")
	@ResponseBody
	public int addCommentLike(@RequestParam("articleId") String articleId,
	                          @RequestParam("respondentId") String respondentId,
	                          @AuthenticationPrincipal Principal principal) {

		String username;
		try {
			username = principal.getName ();
		} catch (NullPointerException e) {
			logger.info ("This user is not login");
			return -1;
		}

		TimeUtil timeUtil = new TimeUtil ();
		CommentLikesRecord commentLikesRecord = new CommentLikesRecord (Long.parseLong (articleId),
				Integer.parseInt (respondentId.substring (1)), userService.findIdByUsername (username), timeUtil.getFormatDateForFive ());
		//是否已点赞
		System.out.println ("commentLikesRecordService.isLiked (commentLikesRecord.getArticleId (), commentLikesRecord.getPId (), username) -------------- " + commentLikesRecordService.isLiked (commentLikesRecord.getArticleId (), commentLikesRecord.getPId (), username));
		if (commentLikesRecordService.isLiked (commentLikesRecord.getArticleId (), commentLikesRecord.getPId (), username)) {
			logger.info ("This user had clicked good for this article");
			return -2;
		} else {
			int likes = commentService.updateLikeByArticleIdAndOriginalAuthorAndId (commentLikesRecord.getArticleId (), commentLikesRecord.getPId ());
			commentLikesRecordService.insertCommentLikesRecord (commentLikesRecord);
			return likes;
		}
	}

}
