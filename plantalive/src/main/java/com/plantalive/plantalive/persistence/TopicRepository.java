package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends CrudRepository<TopicDAO, Long> {
    List<TopicDAO> findAllByPlantId(long plantId);
    List<TopicDAO> findAllByPlantIdNot(long exceptPlantId);
    Optional<TopicDAO> findByTopicName(String topicName);
}
