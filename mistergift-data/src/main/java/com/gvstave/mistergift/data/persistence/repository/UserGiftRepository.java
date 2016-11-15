package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.UserGift;
import com.gvstave.mistergift.data.domain.UserGiftId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserGiftRepository extends CrudRepository<UserGift, UserGiftId>, QueryDslPredicateExecutor<UserGift> {
}
