package com.gvstave.mistergift.data.persistence.mongo.repository;

import com.gvstave.mistergift.data.domain.mongo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String>, QueryDslPredicateExecutor<Comment> {

}
