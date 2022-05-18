package com.example.user.controller;

import com.example.user.dto.DataResultObject;
import com.example.user.dto.mapper.Mapper;
import com.example.user.dto.user.CreateUserRequestDto;
import com.example.user.dto.user.UserResponseDto;
import com.example.user.service.UserService;
import com.example.user.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @Autowired
    public UserController(UserService userService,
                          Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DataResultObject<UserResponseDto>> createUser(
        @RequestBody CreateUserRequestDto userDetails
    ) {
        var userDto = mapper.userDto(userDetails);
        var createdUser = userService.createUser(userDto);
        var userResponseDto = mapper.toUserResponseDto(createdUser);

        return HttpUtils.withStatus(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<UserResponseDto>> getUser(@PathVariable("id") String id) {

        var userDto = userService.getUserByUserId(id);
        var userResponseDto = mapper.toUserResponseDto(userDto);

        return HttpUtils.ok(userResponseDto);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<UserResponseDto>>> getUsers() {

        List<UserResponseDto> userResponseDtos = userService.getUsers().stream()
            .map(mapper::toUserResponseDto)
            .collect(Collectors.toList());

        return HttpUtils.ok(userResponseDtos);
    }
}
