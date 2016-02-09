package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}