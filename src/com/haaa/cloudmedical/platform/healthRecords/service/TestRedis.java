package com.haaa.cloudmedical.platform.healthRecords.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class TestRedis {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-redis.xml");
        RedisTemplate redis = (RedisTemplate) ac.getBean("redisTemplate");
        System.out.println(redis);
        BoundValueOperations<String, String> operations = redis.boundValueOps("aaaa");
        operations.set("bbbb");
        BoundValueOperations obj = redis.boundValueOps("aaaa");
        System.out.println(obj.get());
        redis.delete("aaaa");
        BoundValueOperations obj1 = redis.boundValueOps("aaaa");
        System.out.println(obj1.get());
    }

}
