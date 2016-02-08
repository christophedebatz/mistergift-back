package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.data.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}