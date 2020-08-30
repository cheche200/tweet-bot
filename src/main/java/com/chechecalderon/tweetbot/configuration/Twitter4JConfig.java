package com.chechecalderon.tweetbot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Configuration
public class Twitter4JConfig {

    @Bean
    Twitter getTwitterInstance(){
        return TwitterFactory.getSingleton();
    }

}
