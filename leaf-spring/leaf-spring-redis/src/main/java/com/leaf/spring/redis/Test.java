package com.leaf.spring.redis;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    @Resource
    private static RedisTemplate redisTemplate;

    public static void main(String[] args) {
        testSpringRedis();
    }

    public static void testSpringRedis() {
        //stringRedisTemplate的操作
        // String读写
        redisTemplate.delete("myStr");
        redisTemplate.opsForValue().set("myStr", "skyLine");
        System.out.println(redisTemplate.opsForValue().get("myStr"));
        System.out.println("---------------");

        // List读写
        redisTemplate.delete("myList");
        redisTemplate.opsForList().rightPush("myList", "T");
        redisTemplate.opsForList().rightPush("myList", "L");
        redisTemplate.opsForList().leftPush("myList", "A");
        List<String> listCache = redisTemplate.opsForList().range(
                "myList", 0, -1);
        for (String s : listCache) {
            System.out.println(s);
        }
        System.out.println("---------------");

        // Set读写
        redisTemplate.delete("mySet");
        redisTemplate.opsForSet().add("mySet", "A");
        redisTemplate.opsForSet().add("mySet", "B");
        redisTemplate.opsForSet().add("mySet", "C");
        Set<String> setCache = redisTemplate.opsForSet().members(
                "mySet");
        for (String s : setCache) {
            System.out.println(s);
        }
        System.out.println("---------------");

        // Hash读写
        redisTemplate.delete("myHash");
        redisTemplate.opsForHash().put("myHash", "BJ", "北京");
        redisTemplate.opsForHash().put("myHash", "SH", "上海");
        redisTemplate.opsForHash().put("myHash", "HN", "河南");
        Map<String, String> hashCache = redisTemplate.opsForHash()
                .entries("myHash");
        for (Map.Entry entry : hashCache.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("---------------");
    }
}
