package es.codeurjc.topics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.topics.models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
