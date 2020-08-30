package com.chechecalderon.tweetbot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TwitterService.class})
public class TwitterServiceTest {

    @Autowired
    private TwitterService twitterService;

    @Mock
    private Status mockStatus;

    @Test
    public void shouldBeNewMentionWhenTimeOfLastReplyIsNull(){
        twitterService.setTimeOfLatestStatusReplied(null);
       when(mockStatus.getCreatedAt()).thenReturn(new Date());
       assertTrue(twitterService.isNewMention(mockStatus));
    }

    @Test
    public void shouldBeNewMentionIfStatusCreationTimeIsGTThanTimeOfLatestStatusReplied(){
        twitterService.setTimeOfLatestStatusReplied(LocalDate.parse("2016-06-12"));
        when(mockStatus.getCreatedAt()).thenReturn(new Date());
        assertTrue(twitterService.isNewMention(mockStatus));
    }

    @Test
    public void shouldNotBeNewMentionIfStatusCreationTimeIsLSThanTimeOfLatestStatusReplied(){
        twitterService.setTimeOfLatestStatusReplied(LocalDate.parse("2016-06-12"));
        when(mockStatus.getCreatedAt()).thenReturn(Date.from(LocalDate.parse("2016-05-12").atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()));
        assertFalse(twitterService.isNewMention(mockStatus));
    }


}
