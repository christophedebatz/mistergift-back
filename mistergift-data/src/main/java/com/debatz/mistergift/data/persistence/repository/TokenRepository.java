package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.model.Gift;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Gift, Long> {
}
