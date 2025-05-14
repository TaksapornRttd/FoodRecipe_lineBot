package com.foodrecepi.linebot.controller;

import com.foodrecepi.linebot.service.FetchRecipe;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/webhook")
public class webHookController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private FetchRecipe fetchRecipe;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody CallbackRequest request){
        for(Event event : request.getEvents()){
            if(event instanceof MessageEvent){
                MessageEvent<?> messageEvent = (MessageEvent<?>)  event;
                MessageContent message = messageEvent.getMessage();


                if(message instanceof TextMessageContent){
                    String keyword = ((TextMessageContent) message).getText();
                    String replyToken = messageEvent.getReplyToken();

                    String reply = fetchRecipe.fetchRecipe(keyword);

                    lineMessagingClient.replyMessage(new ReplyMessage(replyToken, Collections.singletonList(new TextMessage(reply))));
                }
            }
        }
        return ResponseEntity.ok("OK");
    }

}
