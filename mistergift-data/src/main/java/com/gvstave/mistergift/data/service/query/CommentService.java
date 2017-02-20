package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.mongo.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service for comments.
 */
@Service
public class CommentService {

    /** The mongo operations. */
    //@Inject
    //private MongoOperations mongoOperations;

    /**
     *
     * @param giftId
     * @return
     */
    public List<Comment> getGiftComments(final Long giftId) {
        Objects.requireNonNull(giftId);
        return new ArrayList<>();
    }

}
