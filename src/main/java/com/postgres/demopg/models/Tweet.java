package com.postgres.demopg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    private String tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by", referencedColumnName = "id")
    private User postedBy;

    public Tweet() {}

    public Tweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTweet() { return tweet; }
    public void setTweet(String tweet) { this.tweet = tweet; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
}