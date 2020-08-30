package com.chechecalderon.tweetbot.controller;


import com.chechecalderon.tweetbot.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

@RestController
public class TwitterController {

    @Autowired
    private Twitter twitter;

    @Autowired
    private TwitterService twitterService;

    private Logger logger = LoggerFactory.getLogger(TwitterController.class);

    @PostMapping("/t4j/tweet")
    public void createTweet(@RequestParam String text) throws TwitterException {
        logger.info("action=twitting text={}",text);
        twitter.updateStatus(text);
    }

    @GetMapping("/t4j/mentions")
    public ResponseList<Status> getMentions() throws Exception{
        return twitter.getMentionsTimeline();
    }

    //Every minute
    @Scheduled(cron="1 * * * * ?")
    @GetMapping("/t4j/interact")
    public void interact() throws Exception{
        ResponseList<Status> mentions= twitter.getMentionsTimeline();
        logger.info("action=fetching numMentions={}",mentions.size());
        mentions.stream().filter(m -> !twitterService.hasBeenReplied(m)).forEach(m -> {
            try {
                twitterService.reply(m);
            } catch (TwitterException e) {
                logger.error("error sending a response", e);
                e.printStackTrace();
            }
        });
    }


}
