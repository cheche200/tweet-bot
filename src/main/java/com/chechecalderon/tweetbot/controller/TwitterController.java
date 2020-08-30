package com.chechecalderon.tweetbot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RestController
public class TwitterController {

    @Autowired
    private Twitter twitter;

    private Logger logger = LoggerFactory.getLogger(TwitterController.class);

    @PostMapping("/t4j/tweet")
    public void createTweet(@RequestParam String text) throws TwitterException {
        twitter.updateStatus(text);
    }

    @Scheduled(cron="1 * * * * ?")
    @GetMapping("/t4j/mentions")
    public void getMentions() throws TwitterException {
        int numMentions = twitter.getMentionsTimeline().size();
        logger.info("action=fetching numMentions={}",numMentions);
    }


}
