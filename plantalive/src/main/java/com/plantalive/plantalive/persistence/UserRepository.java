package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Long> {
    Optional<UserDAO> findByMailAndPassword(String mail, String password);
}
