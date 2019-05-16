package com.blog;

import com.blog.service.ArticleService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBlogApplicationTests {

	@Autowired
	ArticleService service;

	@Test
	public void contextLoads() {
		JSONObject articleManagement = service.getArticleManagement (10, 0);
		System.out.println (articleManagement);
	}

}
