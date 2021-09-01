package com.example.responsesdkadapter.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeneralService {

    private static final JsonParser jsonParser = new JsonParser();
    public static String getTypeAttachment(String json){
        return String.valueOf(jsonParser.parse(json)
                .getAsJsonObject()
                .getAsJsonArray("attachments").get(0)
                .getAsJsonObject()
                .get("type")).replace("\"", "");
    }

    public static String getPayloadURL(String json){
        return String.valueOf(jsonParser.parse(json)
                .getAsJsonObject()
                .getAsJsonArray("attachments").get(0)
                .getAsJsonObject()
                .get("payload")
                .getAsJsonObject()
                .get("url")).replace("\"", "");
    }
    public static JsonElement getPayload(String json){
        return jsonParser.parse(json)
                .getAsJsonObject()
                .getAsJsonArray("attachments").get(0)
                .getAsJsonObject()
                .get("payload");
    }
    public static String getResponseFromZalo(JsonObject jsonObject){
        return String.valueOf(jsonObject.getAsJsonObject("data"));
    }

}
