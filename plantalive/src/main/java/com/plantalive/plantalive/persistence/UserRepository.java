package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Long> {
    boolean existsByIdAndPassword(long id, String password);
}
