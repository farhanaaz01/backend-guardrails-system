package com.Backend_project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class NotificationService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void handleNotification(Long userId, String message) {
        String cooldownKey = "notif_cooldown:user_" + userId;
        String listKey = "user:" + userId + ":pending_notifs";
        Boolean hasCooldown = redisTemplate.hasKey(cooldownKey);

        if (Boolean.TRUE.equals(hasCooldown)) {
            redisTemplate.opsForList().rightPush(listKey, message);
        } else {
            System.out.println("Push Notification Sent: " + message);
            redisTemplate.opsForValue()
                    .set(cooldownKey, "1", Duration.ofMinutes(15));
        }
    }
}
