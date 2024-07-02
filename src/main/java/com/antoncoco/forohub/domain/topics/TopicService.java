package com.antoncoco.forohub.domain.topics;

import com.antoncoco.forohub.domain.courses.Course;
import com.antoncoco.forohub.domain.courses.CourseRepository;
import com.antoncoco.forohub.domain.users.User;
import com.antoncoco.forohub.domain.users.UserRepository;
import com.antoncoco.forohub.infrastructure.errors.ObjectAlreadyPersisted;
import com.antoncoco.forohub.infrastructure.security.JWTService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final EntityManager entityManager;

    public TopicService(TopicRepository topicRepository,
                        CourseRepository courseRepository,
                        UserRepository userRepository,
                        JWTService jwtService,
                        EntityManager entityManager) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.entityManager = entityManager;
    }

    @Transactional
    public Topic addTopic(TopicForm newTopic, String userToken) {
        Course courseRelated = this.courseRepository.findById(newTopic.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course with ID:"+ newTopic.courseId()+" not found"));

        User userRelated = this.userRepository.findByEmail(jwtService.getSubjectFromToken(userToken))
                .orElseThrow(() ->
                        new EntityNotFoundException("Current user was not found, may be the token received is corrupted"));

        Optional<Topic> optionalTopic = this.topicRepository
                .findByTitleAndMessage(newTopic.title(), newTopic.message());
        if(optionalTopic.isPresent()) {
            throw new ObjectAlreadyPersisted("Topic with title " + newTopic.title() + " already exists");
        }

        Topic topicEntity = Topic.builder()
                .title(newTopic.title())
                .message(newTopic.message())
                .course(courseRelated)
                .user(userRelated)
                .build();
        Topic topicSaved = this.topicRepository.save(topicEntity);
        entityManager.refresh(topicSaved);
        return topicSaved;
    }

    public List<Topic> getAllTopics() {
        return this.topicRepository.findAllByStatusIsTrue();
    }

    public Page<Topic> getPageOfTopics(Pageable pageable) {
        return this.topicRepository.findAllByStatusIsTrue(pageable);
    }


    public List<Topic> getFirst10TopicsCreated() {
        return this.topicRepository.findFirst10ByStatusIsTrueOrderByCreatedAtAsc();
    }

    public Topic getTopicById(Integer id) {
        return this.topicRepository.findByIdAndStatusIsTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic with ID:" + id + " not found"));
    }

    @Transactional
    public void deleteTopicById(Integer id) {
        Topic topic = this.topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic with ID " + id + "not found"));
        topic.setStatus(false);
    }

    @Transactional
    public Topic updateTopic(UpdateTopicForm topicModifications, Integer topicId) {
        Topic topicToModify = this.topicRepository.findByIdAndStatusIsTrue(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic with ID:" + topicId + " not found"));

        Optional<Topic> possibleDuplicatedTopic = this.topicRepository
                .findByTitleAndMessage(topicModifications.title(), topicModifications.message());
        if(possibleDuplicatedTopic.isPresent()) {
            throw new ObjectAlreadyPersisted("Topic with the same title or message already exists");
        }
        topicToModify.updateTopic(topicModifications);
        return topicToModify;
    }
}
