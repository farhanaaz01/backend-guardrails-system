package com.Backend_project.demo.controller;

import com.Backend_project.demo.entity.Comment;
import com.Backend_project.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}/comments")
    public Comment addComment(
            @PathVariable Long postId,
            @RequestBody Comment comment) {

        comment.setPostId(postId);
        return commentService.addComment(comment);
    }
}
