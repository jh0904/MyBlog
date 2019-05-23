package com.blog.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * com.blog.repository.es
 *
 * @author jh
 * @date 2019/5/23 18:15
 * description:
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

	List<Book> findBooksByBookNameLike(String name);
}
