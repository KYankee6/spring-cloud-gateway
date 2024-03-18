package com.uicheon.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.netflix.discovery.provider.Serializer;
import com.uicheon.userservice.dto.UserDto;
import com.uicheon.userservice.jpa.UserEntity;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);

	UserDto getUserByUserId(String userId);

	Iterable<UserEntity> getUserByAll();

	UserDto getUserDetailsByEmail(String userName);
}
