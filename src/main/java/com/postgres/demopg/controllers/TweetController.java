package com.postgres.demopg.controllers;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.models.User;
import com.postgres.demopg.repository.TweetRepository;
import com.postgres.demopg.repository.UserRepository;
import com.postgres.demopg.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public Page<Tweet> getTweets(Pageable pageable) {
        return tweetRepository.findAll(pageable);
    }

    @PostMapping("")
    public Tweet createTweet(@Valid @RequestBody Tweet tweet,
                             @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtils.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username).orElseThrow();
        Tweet newTweet = new Tweet(tweet.getTweet(), user);
        return tweetRepository.save(newTweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweetRepository.deleteById(id);
    }
}