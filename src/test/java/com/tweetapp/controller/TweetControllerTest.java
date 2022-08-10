package com.tweetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.UserProfile;
import com.tweetapp.service.TweetAppTweetsServiceImpl;
import com.tweetapp.service.TweetappUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetControllerTest {

    @InjectMocks
    private TweetAppController controller;

    @Mock
    private TweetappUserServiceImpl userImpl;

    @Mock
    private TweetAppTweetsServiceImpl tweetImpl;

    @BeforeEach
    public  void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addNewUserTest(){
        UserProfile userObj = new UserProfile();

        userObj.setLoginid("TestId");
        userObj.setFirstname("TestFirst");
        userObj.setLastname("TestLast");
        userObj.setEmail("testEmail@test.com");
        userObj.setPassword("TestPwd");
        userObj.setConfirmpassword("TestPwd");
        userObj.setContactnumber(9444770238L);
        when(userImpl.registerUser(Mockito.any())).thenReturn(" Successfully Created");
        Assertions.assertEquals( 200,controller.addNewUser(userObj).getStatusCodeValue());
    }

    @Test
    void resetPasswordTest(){
        UserProfile userObj = new UserProfile();
        userObj.setPassword("testPwd");
        when(userImpl.toresetPassword(Mockito.anyString(),Mockito.any())).thenReturn("Password changed successfully");
        Assertions.assertEquals(202,controller.resetPassword("TestId",userObj).getStatusCodeValue());
    }

    @Test
    void getAllUserTest(){
        List<UserProfile> listUser=new ArrayList<>();
        UserProfile userObj = new UserProfile();
        userObj.setLoginid("TestId");
        userObj.setFirstname("TestFirst");
        userObj.setLastname("TestLast");
        userObj.setEmail("testEmail@test.com");
        userObj.setPassword("TestPwd");
        userObj.setConfirmpassword("TestPwd");
        userObj.setContactnumber(9444770238L);

        listUser.add(userObj);
        when(userImpl.getAllUsers()).thenReturn(listUser);
        Assertions.assertEquals(200,controller.getAllUser().getStatusCodeValue());
    }

    @Test
    void postCommentsTest(){
        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        when(tweetImpl.addPost(Mockito.anyString(),Mockito.any())).thenReturn("posted");
        Assertions.assertEquals(200,controller.postComments("TestId",tweetobj).getStatusCodeValue());
    }

    @Test
    void getAllTweetsTest(){
        List<Tweet> listTweet=new ArrayList<>();

        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        listTweet.add(tweetobj);
        when(tweetImpl.getTweets()).thenReturn(listTweet);
        Assertions.assertEquals(200,controller.getAllTweets().getStatusCodeValue());
    }

    @Test
    void updateTweetTest(){
        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        when(tweetImpl.modifyTweet(Mockito.anyString(),Mockito.anyInt(),Mockito.any())).thenReturn(tweetobj);
        Assertions.assertEquals(200,controller.updateTweet("TestId",1,tweetobj).getStatusCodeValue());
    }

    @Test
    void getAllTweetTest(){
        List<Tweet> listTweet=new ArrayList<>();

        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        listTweet.add(tweetobj);
        when(tweetImpl.getTweetByName(Mockito.any())).thenReturn(listTweet);
        Assertions.assertEquals(200,controller.getAllTweet(tweetobj).getStatusCodeValue());
    }

    @Test
    void searchByUserNameTest(){
        List<String> listUser=new ArrayList<>();
        listUser.add("TestId");
        UserProfile userObj = new UserProfile();
        userObj.setLoginid("TestId");
        when(userImpl.userNameSearch(Mockito.any())).thenReturn(listUser);
        Assertions.assertEquals(200,controller.searchByUserName(userObj).getStatusCodeValue());
    }

    @Test
    void deleteTweetTest(){
        when(tweetImpl.removeTweet(Mockito.anyString(),Mockito.anyInt())).thenReturn( "Tweet deleted!!...");
        Assertions.assertEquals(200,controller.deleteingTweet("TestId",1).getStatusCodeValue());
    }

    @Test
    void addlikeTest(){
        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        when(tweetImpl.tweetLike(Mockito.anyString(),Mockito.anyInt())).thenReturn(tweetobj);
        Assertions.assertEquals(200,controller.addlike("TestId",1).getStatusCodeValue());
    }

    @Test
    void replyingTweetTest() throws JsonProcessingException {
        Tweet tweetobj=new Tweet();
        tweetobj.setTweets("Fun Starts");
        String str="{ \"replies\" :\"Let's Start\"} ";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(str);
        when(tweetImpl.addingReply(Mockito.anyString(),Mockito.anyInt(),Mockito.any())).thenReturn(tweetobj);
        Assertions.assertEquals(200,controller.replyingTweet("TestId",1,jsonNode).getStatusCodeValue());
    }
}
