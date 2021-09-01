package com.example.responsesdkadapter.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactData extends BaseData{
    private String messageReactId;
    private String action;
    private String emoji;
    private String reaction;
}
