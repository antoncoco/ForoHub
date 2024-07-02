package com.antoncoco.forohub.controllers;

import com.antoncoco.forohub.domain.topics.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @Operation(summary = "Insert a new topic into the database")
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
    @Operation(summary = "List all available topics in database")
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<TopicResponse> topicsList = this.topicService.getAllTopics()
                .stream().map(TopicResponse::new).toList();
        return ResponseEntity.ok(topicsList);
    }

    @GetMapping("/pagination")
    @Operation(summary = "Get a page with topics")
    public ResponseEntity<Page<TopicResponse>> getAllTopics(
            @ParameterObject Pageable pageable) {
        Page<TopicResponse> topicResponsePage = this.topicService.getPageOfTopics(pageable).map(TopicResponse::new);
        return ResponseEntity.ok(topicResponsePage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an available topic given its ID")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable(name = "id") Integer id) {
        TopicResponse topicResponse = new TopicResponse(topicService.getTopicById(id));
        return ResponseEntity.ok(topicResponse);
    }

    @GetMapping("/first10")
    @Operation(summary = "Get the firsts 10 topic results order by created_at date ASC")
    public ResponseEntity<List<TopicResponse>> getFirst10TopicsCreated() {
        List<TopicResponse> topicResponses = this.topicService.getFirst10TopicsCreated()
                .stream().map(TopicResponse::new).toList();
        return ResponseEntity.ok(topicResponses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update title or content of an available topic given its ID")
    public ResponseEntity<TopicResponse> updateTopic(
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid UpdateTopicForm updateTopicForm) {
        TopicResponse topicResponse = new TopicResponse(this.topicService.updateTopic(updateTopicForm, id));
        return ResponseEntity.ok(topicResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Logical deletion for a topic given its ID")
    public ResponseEntity<?> deleteTopicById(@PathVariable(name = "id") Integer id) {
        this.topicService.deleteTopicById(id);
        return ResponseEntity.noContent().build();
    }
}
