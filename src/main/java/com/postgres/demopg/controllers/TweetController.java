package com.postgres.demopg.controllers;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.models.User;
import com.postgres.demopg.payload.response.TweetResponseDTO;
import com.postgres.demopg.repository.TweetRepository;
import com.postgres.demopg.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public Page<TweetResponseDTO> getTweets(Pageable pageable) {
        Page<Tweet> tweets = tweetRepository.findAll(pageable);
        return tweets.map(TweetResponseDTO::new);
    }

    @PostMapping("/create")
    public Tweet createTweet(@Valid @RequestBody Tweet tweet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = getValidUser(username);
        Tweet myTweet = new Tweet(tweet.getTweet());
        myTweet.setPostedBy(user);
        tweetRepository.save(myTweet);

        return myTweet;
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweetRepository.deleteById(id);
    }

    private User getValidUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }
}