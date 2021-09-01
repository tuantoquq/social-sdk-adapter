package com.example.responsesdkadapter.service;

import com.example.responsesdkadapter.builder.ResponseBuilder;
import com.example.responsesdkadapter.constant.ApplicationConstants;
import com.example.responsesdkadapter.model.request.MessageData;
import com.example.responsesdkadapter.model.request.ReactData;
import com.example.responsesdkadapter.model.response.FacebookResponse;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.MediaAttachment;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {

    private static final Logger logger = LoggerFactory.getLogger(FacebookService.class);
    private final Gson gson = new Gson();
    private final FacebookClient facebookClient = new DefaultFacebookClient(ApplicationConstants.FACEBOOK_ACCESS_TOKEN, Version.VERSION_11_0);
    IdMessageRecipient recipient;

    public FacebookResponse sendText(MessageData mes) {
        recipient = new IdMessageRecipient(mes.getReceiveId());
        Message message = new Message(mes.getText());
        SendResponse response = facebookClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient",recipient),
                Parameter.with("message",message));
        logger.info("### -> response from facebook -> : {}",gson.toJson(response));
        return new FacebookResponse(response.getRecipientId(), response.getMessageId());
    }

    public FacebookResponse sendAttachment(MessageData mes) {

        recipient = new IdMessageRecipient(mes.getReceiveId());
        System.out.println(gson.toJson(mes));
        String attachmentType = GeneralService.getTypeAttachment(gson.toJson(mes));
        String attachmentURL = GeneralService.getPayloadURL(gson.toJson(mes));
        MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.valueOf(attachmentType.toUpperCase()),attachmentURL);
        Message message = new Message(attachment);

        SendResponse response = facebookClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient",recipient),
                Parameter.with("message",message));
        logger.info("### -> response from facebook -> : {}",gson.toJson(response));
        return new FacebookResponse(response.getRecipientId(), response.getMessageId());
    }

}
