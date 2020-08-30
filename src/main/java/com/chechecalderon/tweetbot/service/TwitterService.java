package com.chechecalderon.tweetbot.service;

import com.chechecalderon.tweetbot.util.TwitterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.chechecalderon.tweetbot.util.TwitterUtil.*;


@Service
public class TwitterService implements Sociable {

    private Logger logger = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    private BotChatService botChatService;

    @Autowired
    private Twitter twitter;

    private LocalDate timeOfLatestStatusReplied;

    private Set<Long> repliedStatus = new HashSet<>();

    public void reply(Status mention) throws TwitterException {
        String request = cleanMention(mention.getText());
        String reply = botChatService.getResponse(request);
        logger.info("action=responding user={} text={} createdAt={} reply={}", mention.getUser().getScreenName(), request, mention.getCreatedAt(), reply);

        repliedStatus.add(mention.getId());
        StatusUpdate status = new StatusUpdate("@"+mention.getUser().getScreenName()+" "+reply);
        status.setInReplyToStatusId(mention.getId());
        twitter.updateStatus(status);
    }

    public boolean hasBeenReplied(Status status){
        return repliedStatus.contains(status.getId());
    }

    public boolean isNewMention(Status mention){
       if (timeOfLatestStatusReplied == null)
           return true;

       return timeOfLatestStatusReplied.isBefore(convertToLocalDateViaMillisecond(mention.getCreatedAt()));
    }

    public void setTimeOfLatestStatusReplied(LocalDate timeOfLatestStatusReplied) {
        this.timeOfLatestStatusReplied = timeOfLatestStatusReplied;
    }


}
