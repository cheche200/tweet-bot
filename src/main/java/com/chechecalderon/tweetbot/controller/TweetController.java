package com.chechecalderon.tweetbot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetController {

	@Autowired
	private Twitter twitter;

	@Value("${tweetbot.account}")
	private String botTwitterAccount;

	@GetMapping("/tweets")
	public List<Tweet> getTweets(){
		return twitter.timelineOperations().getUserTimeline(botTwitterAccount);
	}

	@PostMapping("/tweet")
	public void tweet(@RequestParam String text){
		twitter.timelineOperations().updateStatus(text);
	}

	@GetMapping("/mentions")
	public List<Tweet> getMentions(){
		return twitter.timelineOperations().getMentions();
	}
}