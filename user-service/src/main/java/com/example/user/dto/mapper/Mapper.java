package com.example.user.dto.mapper;

import com.example.user.dto.user.CreateUserRequestDto;
import com.example.user.dto.user.UserDto;
import com.example.user.dto.user.UserResponseDto;
import com.example.user.model.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserDto userDto(CreateUserRequestDto createUserRequestDto) {
        return
            UserDto.builder()
                .firstName(createUserRequestDto.getFirstName())
                .lastName(createUserRequestDto.getLastName())
                .email(createUserRequestDto.getEmail())
                .password(createUserRequestDto.getPassword())
                .build();
    }

    public UserDto toUserDto(UserEntity userEntity) {
        return
            UserDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    public UserEntity toUserEntity(UserDto userDto) {
        return
            UserEntity.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .encryptedPassword(userDto.getEncryptedPassword())
                .build();
    }

    public UserResponseDto toUserResponseDto(UserDto userDto) {
        return UserResponseDto.builder()
            .userId(userDto.getUserId())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getEmail())
            .build();
    }
}
