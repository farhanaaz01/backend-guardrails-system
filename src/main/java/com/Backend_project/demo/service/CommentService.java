package com.Backend_project.demo.service;

import com.Backend_project.demo.entity.Comment;
import com.Backend_project.demo.entity.Post;
import com.Backend_project.demo.repository.CommentRepository;
import com.Backend_project.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ViralityService viralityService;

    @Autowired
    private PostRepository postRepository;

    public Comment addComment(Comment comment) {
        if (comment.getDepthLevel() > 20) {
            throw new RuntimeException("Max depth exceeded");
        }
        if (comment.getAuthorType().name().equals("BOT")) {
            String botKey = "post:" + comment.getPostId() + ":bot_count";
            Long count = redisTemplate.opsForValue().increment(botKey);
            if (count > 100) {
                throw new RuntimeException("429 Too Many Bot Replies");
            }
            Post post = postRepository.findById(comment.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            Long humanId = post.getAuthorId();
            String cooldownKey = "cooldown:bot_" + comment.getAuthorId() + ":human_" + humanId;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Cooldown active");
            }
            redisTemplate.opsForValue()
                    .set(cooldownKey, "1", Duration.ofMinutes(10));
            viralityService.updateScore(comment.getPostId(), "BOT_REPLY");
            
        }
        return commentRepository.save(comment);
    }
}
