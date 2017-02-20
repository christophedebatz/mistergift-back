package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.mongo.Comment;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Comment}.
 */
@Repository
public interface CommentRepository extends EntityRepository<Comment, ObjectId> {
}