package com.postgres.demopg.payload.response;

import com.postgres.demopg.models.Tweet;

public class TweetResponseDTO {
    private Long id;
    private String tweet;
    private String postedBy;

    public TweetResponseDTO(Tweet tweet) {
        this.id = tweet.getId();
        this.tweet = tweet.getTweet();
        this.postedBy = tweet.getPostedBy() != null ? tweet.getPostedBy().getUsername() : null;
    }

    public Long getId() { return id; }
    public String getTweet() { return tweet; }
    public String getPostedBy() { return postedBy; }
}