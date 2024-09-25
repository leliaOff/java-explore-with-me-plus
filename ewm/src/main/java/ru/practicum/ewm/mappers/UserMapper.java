package ru.practicum.ewm.mappers;

import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.models.User;

public class UserMapper {
    public static UserShortDto toShortDto(User model) {
        return new UserShortDto(
                model.getId(),
                model.getName()
        );
    }

    public static UserDto toDto(User model) {
        return new UserDto(
                model.getId(),
                model.getEmail(),
                model.getName()
        );
    }

    public static User toModel(NewUserRequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        return user;
    }
}
