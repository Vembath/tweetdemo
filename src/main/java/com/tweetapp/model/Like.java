package com.tweetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    private Map<String,Boolean> userliked;

    public Map<String, Boolean> getUserliked() {
        return userliked;
    }

    public void setUserliked(Map<String, Boolean> userliked) {
        this.userliked = userliked;
    }
}
