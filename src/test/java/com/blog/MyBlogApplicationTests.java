package com.blog;

import com.blog.mapper.ArticleMapper;
import com.blog.model.Article;
import com.blog.repository.es.Book;
import com.blog.repository.es.BookRepository;
import com.blog.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBlogApplicationTests {

	@Autowired
	ArticleService service;
	@Autowired
	ArticleMapper mapper;
	//@Autowired
	//JestClient jestClient;

	@Autowired
	BookRepository bookRepository;

	@Test
	public void contextLoads() {
		List<Article> articles = mapper.findAllArticles ();
		articles.forEach (System.out::println);
	}

	//@Test
	//public void test1() {
	//	Article articles = mapper.findArticleById (2);
	//	Index index = new Index.Builder (articles).index ("magic").type ("news").build ();
	//	try {
	//		jestClient.execute (index);
	//	} catch (IOException e) {
	//		e.printStackTrace ();
	//	}
	//
	//}

	//@Test
	//public void search(){
	//	String json="{\n" +
	//			"    \"query\" : {\n" +
	//			"        \"match\" : {\n" +
	//			"            \"articleContent\" : \"博客\"\n" +
	//			"        }\n" +
	//			"    },\n" +
	//			"    \"highlight\": {\n" +
	//			"        \"fields\" : {\n" +
	//			"            \"articleContent\" : {}\n" +
	//			"        }\n" +
	//			"    }\n" +
	//			"}";
	//	Search build = new Search.Builder (json).addIndex ("magic").addType ("news").build ();
	//
	//	try {
	//		SearchResult result = jestClient.execute (build);
	//		System.out.println (result.getJsonString ());
	//	} catch (IOException e) {
	//		e.printStackTrace ();
	//	}
	//}

	@Test
	public void test2(){
		Book book = new Book ();
		book.setId (1);
		book.setBookName ("aaa");
		book.setAuthor ("jh");
		Book book1 = new Book ();
		book1.setId (2);
		book1.setBookName ("abbb");
		book1.setAuthor ("jh");
		Book book2 = new Book ();
		book2.setId (3);
		book2.setBookName ("gccc");
		book2.setAuthor ("jh");
		Book book3 = new Book ();
		book3.setId (4);
		book3.setBookName ("jhddd");
		book3.setAuthor ("jh");
		List<Book> list=new ArrayList<> ();
		list.add (book);
		list.add (book1);
		list.add (book2);
		list.add (book3);
		bookRepository.save (list);
	}

	@Test
	public void test3(){
		List<Book> a = bookRepository.findBooksByBookNameLike ("a");
		for (Book book : a) {
			System.out.println ("book = " + book);
		}
	}
}
