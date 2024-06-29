package com.antoncoco.forohub.domain.topics;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<TopicResponse> addTopic(
            @RequestBody @Valid TopicForm newTopic,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            UriComponentsBuilder uriBuilder) {
        String userToken = authorization.substring(7);
        Topic topicPersisted = this.topicService.addTopic(newTopic, userToken);
        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topicPersisted.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicResponse(topicPersisted));
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<TopicResponse> topicsList = this.topicService.getAllTopics()
                .stream().map(TopicResponse::new).toList();
        return ResponseEntity.ok(topicsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable(name = "id") Integer id) {
        TopicResponse topicResponse = new TopicResponse(topicService.getTopicById(id));
        return ResponseEntity.ok(topicResponse);
    }
}
