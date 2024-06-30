package com.antoncoco.forohub.domain.topics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findByTitleAndMessage(String title, String message);
    List<Topic> findAllByStatusIsTrue();
    Optional<Topic> findByIdAndStatusIsTrue(Integer id);
}
