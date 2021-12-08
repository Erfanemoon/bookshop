package erfan.codes.bookshop.repositories.redis_repo;

public interface IRedisSessionRepo {

    void put(String key, String value);

    String get(String key);

    void delete(String key);

//    void flushAll();

    boolean expire(String key, long seconds);

//    Long add(String key, String... values);
}
