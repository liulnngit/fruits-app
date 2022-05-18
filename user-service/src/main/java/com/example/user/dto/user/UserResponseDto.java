package com.example.user.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    String userId;
    String firstName;
    String lastName;
    String email;
}
