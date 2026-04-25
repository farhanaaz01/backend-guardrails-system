package com.Backend_project.demo.entity;

import com.Backend_project.demo.Enum.AuthorType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    @Enumerated(EnumType.STRING)
    private AuthorType authorType;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt;
}
