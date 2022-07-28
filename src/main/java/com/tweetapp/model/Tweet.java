package com.tweetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Document(collection = "tweetTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    @Id
    private int tweetId;
    private String userName;
    @NotBlank
    @Size(min=1,max=144,message="Tweet should not beyond 144 characters")
    private String tweets;
    private Tagging tag;
    private List<Like>likedby;
    private List<Reply> replypost;
    private Date postedat;

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    public Tagging getTag() {
        return tag;
    }

    public void setTag(Tagging tag) {
        this.tag = tag;
    }


    public List<Like> getLikedby() {
        return likedby;
    }

    public void setLikedby(List<Like> likedby) {
        this.likedby = likedby;
    }

    public List<Reply> getReplypost() {
        return replypost;
    }

    public void setReplypost(List<Reply> replypost) {
        this.replypost = replypost;
    }

    public Date getPostedat() {
        return postedat;
    }

    public void setPostedat(Date postedat) {
        this.postedat = postedat;
    }
}
