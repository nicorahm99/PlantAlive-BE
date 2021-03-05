package com.plantalive.plantalive.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends CrudRepository<TopicDAO, Long> {
    List<TopicDAO> findAllByPlantIdIsNull();
    Optional<TopicDAO> findByTopicName(String topicName);
}
