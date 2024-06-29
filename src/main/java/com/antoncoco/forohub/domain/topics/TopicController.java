package com.antoncoco.forohub.domain.topics;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
}