package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link GiftComment}.
 */
@Repository
public interface GiftCommentRepository extends EntityRepository<GiftComment, Long> {
}
