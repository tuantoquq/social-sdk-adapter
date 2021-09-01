package com.example.responsesdkadapter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseData {
    protected String sendTo;
    protected String receiveId;
    protected String senderId;
}
