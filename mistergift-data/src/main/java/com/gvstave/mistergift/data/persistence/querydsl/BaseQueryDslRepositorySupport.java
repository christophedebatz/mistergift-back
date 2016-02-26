package com.gvstave.mistergift.data.persistence.querydsl;

import com.mysema.query.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Middle class with some useful paging methods.
 *
 * @param <T> The entity type.
 */
public class BaseQueryDslRepositorySupport<T> extends QueryDslRepositorySupport {

    /**
     * Default query DSL constructor.
     *
     * @param domainClass The domain class.
     */
    protected BaseQueryDslRepositorySupport(Class<T> domainClass) {
        super(domainClass);
    }

    /**
     * Returns a result page.
     *
     * @param count    The results count.
     * @param results  The results list.
     * @param pageable The pageable.
     * @return The page.
     */
    protected Page<T> buildPage(long count, List<T> results, Pageable pageable) {
        if (count > 0) {
            return new PageImpl<>(results, pageable, count);
        }

        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    /**
     * Apply the pagination to the query with the pageable object.
     *
     * @param query    The query.
     * @param pageable The pageable.
     * @return The query.
     */
    protected JPQLQuery applyPagination(JPQLQuery query, Pageable pageable) {
        if (pageable != null) {
            return query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        }

        return query;
    }
}
