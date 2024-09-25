package ru.practicum.ewm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewUserRequest {
    private String email;
    private String name;
}
