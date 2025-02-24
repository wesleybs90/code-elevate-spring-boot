package com.example.code_elevate.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void put(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }
}