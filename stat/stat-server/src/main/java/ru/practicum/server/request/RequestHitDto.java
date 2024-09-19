package ru.practicum.server.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
