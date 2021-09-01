package com.example.responsesdkadapter.service;

import com.example.responsesdkadapter.builder.ResponseBuilder;
import com.example.responsesdkadapter.constant.ApplicationConstants;
import com.example.responsesdkadapter.model.request.MessageData;
import com.example.responsesdkadapter.model.response.FacebookResponse;
import com.example.responsesdkadapter.model.response.ZaloResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vng.zalo.sdk.APIException;
import com.vng.zalo.sdk.oa.ZaloOaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZaloService{
    private final Logger logger = LoggerFactory.getLogger(ZaloService.class);
    private final ZaloOaClient client = new ZaloOaClient();
    private final Map<String, Object> params = new HashMap<>();
    private final Gson gson = new Gson();

    public ResponseBuilder sendText(MessageData mes) {
        ResponseBuilder builder;
        if(!mes.getSendTo().equals("Zalo")){
            builder = new ResponseBuilder.Builder(415)
                    .buildMessage("field sendTo must be equal 'Zalo'")
                    .build();
            return builder;
        }
        params.put("access_token", ApplicationConstants.ZALO_ACCESS_TOKEN);
        JsonObject id = new JsonObject();
        id.addProperty("user_id",mes.getReceiveId());
        JsonObject text = new JsonObject();
        text.addProperty("text",mes.getText());
        return getResponseBuilder(id, text);
    }


    public ResponseBuilder sendAttachment(MessageData mes) {
        ResponseBuilder builder;
        params.put("access_token",ApplicationConstants.ZALO_ACCESS_TOKEN);
        if(!mes.getSendTo().equals("Zalo")){
            builder = new ResponseBuilder.Builder(415)
                    .buildMessage("field sendTo must be equal 'Zalo'")
                    .build();
            return builder;
        }
        JsonObject id = new JsonObject();
        id.addProperty("user_id",mes.getReceiveId());
        JsonObject attachment = new JsonObject();
        attachment.addProperty("type",GeneralService.getTypeAttachment(gson.toJson(mes)));
        attachment.add("payload",GeneralService.getPayload(gson.toJson(mes)));
        return getResponseBuilder(id, attachment);
    }

    private ResponseBuilder getResponseBuilder(JsonObject id, JsonObject attachment) {
        ResponseBuilder builder;
        JsonObject body = new JsonObject();
        body.add("recipient",id);
        body.add("message",attachment);
        try {
            JsonObject response = client.excuteRequest(ApplicationConstants.ZALO_URL_API,"POST",params,body);
            ZaloResponse res = gson.fromJson(GeneralService.getResponseFromZalo(response),ZaloResponse.class);
            logger.info("response: {}",response);
            builder = new ResponseBuilder.Builder(200)
                    .buildMessage("successfully")
                    .buildData(res)
                    .build();
        } catch (APIException e) {
            logger.error("error: {}",e.getMessage());
            builder = new ResponseBuilder.Builder(400)
                    .buildMessage("cant call to zalo api")
                    .build();
        }
        return builder;
    }
}
