package com.blog.repository.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * com.blog.repository.es
 *
 * @author jh
 * @date 2019/5/23 18:16
 * description:
 */
@Data
@Document (indexName = "magic",type = "book")
public class Book {
	private Integer id;
	private String bookName;
	private String author;
}
