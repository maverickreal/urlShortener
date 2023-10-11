package com.maverick.url_shortener.api.controller.LinkController;

import lombok.Value;

@Value
public class CreateLinkResponse {
    String shortenedLink;
}