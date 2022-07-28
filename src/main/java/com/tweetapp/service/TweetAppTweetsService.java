package com.tweetapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tweetapp.model.Tweet;

import java.util.List;

public interface TweetAppTweetsService {

    public String addPost(String userName, Tweet tweets);

    public List<Tweet> getTweets();

    public Tweet modifyTweet(String userName,int id,Tweet tweets);

    public List<Tweet> getTweetByName(Tweet tweet);

    public String removeTweet(String userName,int id);

    public Tweet tweetLike(String userName,int id);

    public Tweet addingReply(String userName, int id, JsonNode reply);
}
