package com.chechecalderon.tweetbot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@RestController
public class TweetController {

	@Autowired
	private Twitter twitter;

	@GetMapping("/tweets")
	public List<Tweet> getTweets(){
		return twitter.timelineOperations().getUserTimeline("chechecalderon");
	}

	@PostMapping("/tweet")
	public void tweet(@RequestParam String text){
		twitter.timelineOperations().updateStatus(text);
	}


	@GetMapping("/mentions")
	public List<Tweet> getMentions() throws TwitterException {
		return twitter.timelineOperations().getMentions();
	}


}