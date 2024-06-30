package com.antoncoco.forohub.domain.topics;

import com.antoncoco.forohub.domain.courses.Course;
import com.antoncoco.forohub.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer id;

    @Column(name = "topic_title")
    private String title;

    @Column(name = "topic_message")
    private String message;

    @Column(name = "topic_created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "topic_status", insertable = false)
    @Setter
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "topic_author")
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_course")
    private Course course;

    public void updateTopic(UpdateTopicForm updateTopicForm) {
        if (updateTopicForm.title() != null) {
            this.title = updateTopicForm.title();
        }
        if (updateTopicForm.message() != null) {
            this.message = updateTopicForm.message();
        }
    }
}
