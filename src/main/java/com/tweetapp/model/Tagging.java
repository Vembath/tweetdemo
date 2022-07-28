package com.tweetapp.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tagging {

    private String tagid;

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }
}
