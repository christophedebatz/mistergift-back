package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Whishlist;
import com.gvstave.mistergift.data.domain.WhishlistId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface WhishlistRepository extends CrudRepository<Whishlist, WhishlistId>, QueryDslPredicateExecutor<Whishlist> {
}
