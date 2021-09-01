package com.example.responsesdkadapter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZaloResponse {
    @JsonProperty("messageId")
    private String message_id;

    @JsonProperty("userId")
    private String user_id;
}
