package com.Backend_project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViralityService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void updateScore(Long postId, String actionType) {
        String key = "post:" + postId + ":virality_score";
        int score = switch (actionType) {
            case "BOT_REPLY" -> 1;
            case "LIKE" -> 20;
            case "COMMENT" -> 50;
            default -> 0;
        };
        redisTemplate.opsForValue().increment(key, score);
    }
}