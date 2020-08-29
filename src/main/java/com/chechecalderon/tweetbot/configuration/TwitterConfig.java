package com.chechecalderon.tweetbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Configuration
public class TwitterConfig {

    @Value("${spring.social.twitter.appId}")
    private String consumerKey;
    @Value("${spring.social.twitter.appSecret}")
    private String consumerSecret;
    @Value("${twitter.access.token}")
    private String accessToken;
    @Value("${twitter.access.token.secret}")
    private String accessTokenSecret;

    /*
    * https://github.com/spring-projects/spring-social/issues/317
    *
    *  spring 5.1.5.RELEASE the default content-type change to application/x-www-form-urlencoded;charset:UTF-8
    *  so sign the wrong signature.
    *
    * */
    public class ContentTypeInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept (HttpRequest request, byte[] body, ClientHttpRequestExecution execution )
                throws IOException {
            request.getHeaders().setContentType(APPLICATION_FORM_URLENCODED); return execution.execute(request, body);
        }
    }

    @Bean
    TwitterTemplate getTwtTemplate(){
        TwitterTemplate t = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        t.getRestTemplate().getInterceptors().add (0, new ContentTypeInterceptor());
        return t;
    }
}
