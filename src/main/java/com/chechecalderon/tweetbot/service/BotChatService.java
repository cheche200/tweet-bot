package com.chechecalderon.tweetbot.service;

import org.alicebot.ab.*;
import org.alicebot.ab.utils.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BotChatService {

    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    String resourcesPath = getResourcesPath();
    Bot bot = new Bot("super", resourcesPath);

    public String getResponse(String request) {
        return getBotResponse(request);
    }

    private String getBotResponse(String humanRequest) {
        String response = "";
        try {

            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;

            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "Sorry I don't understand";
            System.out.print("Human : " + textLine);
            textLine = humanRequest;
            
            if (isRequestLegit(textLine)) {
                String request = textLine;
                if (MagicBooleans.trace_mode)
                    System.out.println(
                            "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                    + ":TOPIC=" + chatSession.predicates.get("topic"));
                response = chatSession.multisentenceRespond(request);
                while (response.contains("&lt;"))
                    response = response.replace("&lt;", "<");
                while (response.contains("&gt;"))
                    response = response.replace("&gt;", ">");
                System.out.println("Robot : " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private boolean isRequestLegit(String textLine){
        if ((textLine == null) || (textLine.length() < 1)) {
            textLine = MagicStrings.null_input;
            return false;
        }
        if (textLine.equals("q")) {
            System.exit(0);
            return false;
        } else if (textLine.equals("wq")) {
            bot.writeQuit();
            System.exit(0);
            return false;
        }
        return true;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }



}
