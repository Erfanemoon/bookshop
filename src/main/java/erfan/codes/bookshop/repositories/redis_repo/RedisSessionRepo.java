package erfan.codes.bookshop.repositories.redis_repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisSessionRepo implements IRedisSessionRepo {

    private final String hashRef = "String";

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;

    @Autowired
    public RedisSessionRepo(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void put(String key, String value) {

        this.hashOperations.put(hashRef, key, value);
    }

    @Override
    public String get(String key) {
        return this.hashOperations.get(hashRef, key);
    }

    @Override
    public void delete(String key) {
        this.hashOperations.delete(hashRef, key);
    }

//    @Override
//    public void flushAll() {
//        this.hashOperations.
//    }

    @Override
    public boolean expire(String key, long seconds) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, seconds, TimeUnit.SECONDS));
    }

//    @Override
//    public Long add(String key, String... values) {
//        this.hashOperations.return null;
//    }
}
