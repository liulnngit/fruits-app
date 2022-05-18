package com.example.user.service;

import com.example.user.dto.mapper.Mapper;
import com.example.user.dto.user.UserDto;
import com.example.user.exception.DuplicateUserException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final String USER_NOT_FOUND_BY_USERNAME = "User not found, username = ";
    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found, email = ";
    public static final String USER_NOT_FOUND_BY_ID = "User not found, id = ";

    private final UserRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Mapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           Mapper mapper) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        var optionalUser = usersRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new DuplicateUserException(
                String.format("User '%s' already exists", userDto.getEmail())
            );
        }

        var updatedUserDto = userDto.toBuilder()
            .userId(UUID.randomUUID().toString())
            .encryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
            .build();

        var userEntity = mapper.toUserEntity(updatedUserDto);
        var saved = usersRepository.save(userEntity);

        log.info("User {} {} created", saved.getFirstName(), saved.getLastName());

        return mapper.toUserDto(saved);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = usersRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_USERNAME + username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,
            true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        var userEntity = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email));

        return mapper.toUserDto(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        var userEntity = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_ID + userId));

        return mapper.toUserDto(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false)
            .map(mapper::toUserDto)
            .collect(Collectors.toList());
    }
}
