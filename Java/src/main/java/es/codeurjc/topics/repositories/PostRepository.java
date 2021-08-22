package es.codeurjc.topics.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.topics.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTopicIdAndId(Long topicId, Long postId);

    Collection<Post> findByUserId(Long userId);
}
