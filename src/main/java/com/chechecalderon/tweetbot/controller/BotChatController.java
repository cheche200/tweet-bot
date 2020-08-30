package com.chechecalderon.tweetbot.controller;


import com.chechecalderon.tweetbot.service.BotChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotChatController {

    @Autowired
    BotChatService botChatService;

    @Autowired
    TweetController tweetController;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String getResponse(@RequestBody String request){
        String response = botChatService.getResponse(request);
        return response;
    }


}
