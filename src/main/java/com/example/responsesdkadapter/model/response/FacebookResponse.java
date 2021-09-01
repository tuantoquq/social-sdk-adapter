package com.example.responsesdkadapter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookResponse {
    @JsonProperty("userId")
    private String recipientId;

    @JsonProperty("messageId")
    private String messageId;
}
