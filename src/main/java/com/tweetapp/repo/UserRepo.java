package com.tweetapp.repo;

import com.tweetapp.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserProfile,String> {
    //save


    @Override
    Optional<UserProfile> findById(String str);

    @Query("{ 'loginid' : { $regex: ?0 }}")
    List<UserProfile> getUsers(String name);
}
