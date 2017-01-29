package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Link;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Link}.
 */
@Service
public class LinkPersistenceService extends BaseEntityPersistenceService<Link, Long> {

    /**
     * Constructor.
     */
    public LinkPersistenceService() {
        super(Link.class);
    }

}
