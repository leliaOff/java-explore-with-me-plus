package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.dto.HitDto;
import ru.practicum.ewm.client.StatClient;

import java.time.LocalDateTime;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        StatClient statClient = context.getBean(StatClient.class);
        statClient.hit(new HitDto(null, "app", "uri", "ip", LocalDateTime.parse("2020-05-05 00:00:00")));
    }
}