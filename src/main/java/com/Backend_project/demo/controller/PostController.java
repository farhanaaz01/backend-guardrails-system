package com.Backend_project.demo.controller;


import com.Backend_project.demo.entity.Post;
import com.Backend_project.demo.service.PostService;
import com.Backend_project.demo.service.ViralityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ViralityService viralityService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        viralityService.updateScore(postId, "LIKE");
        return "Post liked successfully";
    }
}