package com.ferratum.rabbit.api;

import lombok.Data;

@Data
public class HeaderRequest {

    private String headerValue;
    private String messageText;
}
