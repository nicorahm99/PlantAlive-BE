package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlantRepository extends CrudRepository<PlantDAO, Long> {
    List<PlantDAO> findAllByOwnerId(long ownerId);
}
