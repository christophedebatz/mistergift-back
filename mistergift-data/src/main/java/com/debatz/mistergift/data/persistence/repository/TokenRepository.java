package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.data.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
