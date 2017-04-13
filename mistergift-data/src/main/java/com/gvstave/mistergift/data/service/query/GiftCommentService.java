package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The gift comment service.
 */
@Service
public class GiftCommentService {

    /** The gift comment persistence service. */
    @Inject
    private GiftCommentPersistenceService giftCommentPersistenceService;

    /**
     * Returns the comments for a gift.
     * @param giftId   The gift id.
     * @param pageable The pageable.
     * @return The gift comments.
     */
    @Transactional(readOnly = true)
    public Page<GiftComment> getGiftComments(Long giftId, Pageable pageable) {
        return giftCommentPersistenceService.findAll(QGiftComment.giftComment.object.id.eq(giftId), pageable);
    }

    /**
     * Returns the comments for a gift and a user.
     * @param giftId   The commented gift.
     * @param userId   The author of the comment.
     * @param pageable The pageable.
     * @return
     */
    @Transactional(readOnly = true)
    public Page<GiftComment> getUserGiftComments(Long giftId, Long userId, Pageable pageable) {
        QGiftComment giftComment = QGiftComment.giftComment;
        return giftCommentPersistenceService.findAll(giftComment.object.id.eq(giftId).and(giftComment.author.id.eq(userId)), pageable);
    }

}
