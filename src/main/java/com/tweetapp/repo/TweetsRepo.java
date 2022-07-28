package com.tweetapp.repo;

import com.tweetapp.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepo extends MongoRepository<Tweet,Integer> {

    @Query("{ 'userName' : ?0 }")
    List<Tweet> findByuserName(String name);

    @Override
    void deleteById(Integer integer);
}
