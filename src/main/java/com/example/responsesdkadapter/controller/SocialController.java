package com.example.responsesdkadapter.controller;

import com.example.responsesdkadapter.builder.ResponseBuilder;
import com.example.responsesdkadapter.model.request.MessageData;
import com.example.responsesdkadapter.service.FacebookService;
import com.example.responsesdkadapter.service.ZaloService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendMessage")
public class SocialController {
    @Autowired
    private FacebookService facebookService;

    @Autowired
    private ZaloService zaloService;
    Gson gson = new Gson();

    @PostMapping(path = "/facebook",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBuilder> sendMessage(@RequestBody String data){
        MessageData mes = gson.fromJson(data, MessageData.class);
        ResponseBuilder res;
        if(!mes.getSendTo().equals("Facebook")){
            if(mes.getAttachments() == null){
                res = new ResponseBuilder.Builder(200)
                        .buildMessage("successfully")
                        .buildData(facebookService.sendText(mes))
                        .build();
            }else{
                res = new ResponseBuilder.Builder(200)
                        .buildMessage("successfully")
                        .buildData(facebookService.sendAttachment(mes))
                        .build();
            }
        }else{
            res = new ResponseBuilder.Builder(415)
                    .buildMessage("field sendTo must be equal 'Facebook'")
                    .build();
        }


        return new ResponseEntity<>(res, HttpStatus.valueOf(res.getCode()));
    }

    @PostMapping(
            path = "/zalo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseBuilder> sendMessageToZalo(@RequestBody String data){
        MessageData mes = gson.fromJson(data, MessageData.class);
        ResponseBuilder builder;
        if(mes.getText() != null){
            builder = zaloService.sendText(mes);
        }else{
            builder = zaloService.sendAttachment(mes);
        }

        return new ResponseEntity<>(builder,HttpStatus.valueOf(builder.getCode()));
    }
}
