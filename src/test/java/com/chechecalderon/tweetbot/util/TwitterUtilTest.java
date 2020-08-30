package com.chechecalderon.tweetbot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TwitterUtilTest {


    @Test
    public void shouldRemoveMentionFromText(){
        assertEquals("Hola", TwitterUtil.cleanMention("@ChecheCalderon Hola"));
    }


}
