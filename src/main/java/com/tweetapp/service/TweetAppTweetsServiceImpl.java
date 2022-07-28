package com.tweetapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.*;
import com.tweetapp.repo.TweetsRepo;
import com.tweetapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class TweetAppTweetsServiceImpl implements  TweetAppTweetsService{

    @Autowired
    private TweetsRepo tweetRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KafkaTemplate<String,String> kafkaTem;

    @Override
    public String addPost(String userName, Tweet tweets) {

        Random random = new Random();
        LocalDate local=  LocalDate.now();
        Tweet tweet=new Tweet();
        if(userRepo.findById(userName).isPresent()) {
            tweet.setUserName(userName);
            tweet.setTweetId(random.nextInt());
            tweet.setTweets(tweets.getTweets());
            tweet.setPostedat(Date.valueOf(local.now()));
            tweetRepo.save(tweet);
            kafkaTem.send("tweetapp",String.valueOf(tweet.getTweetId()),"Tweet added");
            return tweets.getTweets() + "(posted)";
        }
        throw new UserNotFoundException(Constants.USERNOTFOUND);
    }

    @Override
    public List<Tweet> getTweets() {
        return tweetRepo.findAll();
    }

    @Override
    public Tweet modifyTweet(String userName, int id,Tweet tweet) {
        if(userRepo.findById(userName).isPresent()) {
            if(tweetRepo.findById(id).isPresent()){
                Tweet tweetdata=tweetRepo.findById(id).get();
                tweetdata.setTweets(tweet.getTweets());
                tweetRepo.save(tweetdata);
                return tweetdata;
            }
           throw new TweetNotFoundException(Constants.INVALIDTWEETID);
        }
        throw new UserNotFoundException(Constants.USERNOTFOUND);

    }

    @Override
    public List<Tweet> getTweetByName(Tweet tweet) {
        if(tweetRepo.findByuserName(tweet.getUserName())!=null){
            return tweetRepo.findByuserName(tweet.getUserName());
        }
        throw new UserNotFoundException(Constants.INVALIDTWEETIDORUSERNAME);

    }

    @Override
    public String removeTweet(String userName, int id) {
        if(userRepo.findById(userName).isPresent()) {
            if(tweetRepo.findById(id).isPresent()){
               tweetRepo.deleteById(id);
                return "Tweet deleted!!...";
            }
            throw new TweetNotFoundException(Constants.INVALIDTWEETID);
        }
        throw new UserNotFoundException(Constants.USERNOTFOUND);
    }

    @Override
    public Tweet tweetLike(String userName, int id) {
        Tweet tweets =new Tweet();
        List<Like> likeData=new LinkedList<>();

        Like likeobj=new Like();
        if(userRepo.findById(userName).isPresent()){
            if((tweetRepo.findById(id).isPresent())){
                tweets=tweetRepo.findById(id).get();
                if(tweets.getLikedby()==null){
                    Map<String,Boolean> mapObj=new LinkedHashMap<>();
                    mapObj.put(userName,Boolean.TRUE);
                    likeobj.setUserliked(mapObj);
                    likeData.add(likeobj);
                    tweets.setLikedby(likeData);
                }
                else {
                        if (!tweets.getLikedby().stream().anyMatch(r->r.getUserliked().containsKey(userName))) {
                            Map<String, Boolean> mapObj = new LinkedHashMap<>();
                            mapObj.put(userName, Boolean.TRUE);
                            likeobj.setUserliked(mapObj);
                            tweets.getLikedby().add(likeobj);
                        }
                        else {
                            tweets.getLikedby().removeIf(item->item.getUserliked().containsKey(userName));
                        }
                    }
                tweetRepo.save(tweets);
                kafkaTem.send("tweetapp",String.valueOf(tweets.getTweetId()),"Tweet liked");
         return tweets;
            }
        throw new TweetNotFoundException(Constants.INVALIDTWEETID);

        }
            throw new UserNotFoundException(Constants.USERNOTFOUND);

        }

    @Override
    public Tweet addingReply(String userName, int id, JsonNode reply) {
        LocalDate local=  LocalDate.now();
        List<Reply> repiles=new LinkedList<>();
        Tweet tweets=new Tweet();
        Reply rep=new Reply();
        if(userRepo.findById(userName).isPresent()) {
            if ((tweetRepo.findById(id).isPresent())) {
                tweets = tweetRepo.findById(id).get();
                rep.setRepliedat(Date.valueOf(local.now()));
                rep.setReplyid(userName);
                rep.setReplies(reply.asText());
                if (reply.hasNonNull("tagged")) {
                    Tagging tags = new Tagging();
                    tags.setTagid(reply.get("tagged").asText());
                    rep.setTagged(tags);
                }
                repiles.add(rep);
                tweets.setReplypost(repiles);
                tweetRepo.save(tweets);

                return tweets;
            }
            throw new TweetNotFoundException(Constants.INVALIDTWEETID);

            }
        throw new UserNotFoundException(Constants.USERNOTFOUND);
        }


}


