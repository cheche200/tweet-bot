package com.chechecalderon.tweetbot.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class TwitterUtil {

    public static LocalDate convertToLocalDateViaMillisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static String cleanMention(String text){
        return StringUtils.remove(text, "@ChecheCalderon").trim();
    }
}
