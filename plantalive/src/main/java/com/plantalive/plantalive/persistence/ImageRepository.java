package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<ImageDAO, Long> {
    Optional<ImageDAO> findByPlantId(Long plantId);
}
