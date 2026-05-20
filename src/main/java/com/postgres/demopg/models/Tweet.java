package com.postgres.demopg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // Campo calculado para devolver solo el username en el JSON
    @Transient
    private String username;

    public Tweet() {
    }

    public Tweet(String tweet, User user) {
        this.tweet = tweet;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Esto hace que el JSON incluya "username" aunque no sea columna en BD
    public String getUsername() {
        return user != null ? user.getUsername() : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}