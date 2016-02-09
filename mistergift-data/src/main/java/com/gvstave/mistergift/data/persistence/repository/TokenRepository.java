package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
