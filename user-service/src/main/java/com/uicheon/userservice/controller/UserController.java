package com.uicheon.userservice.controller;

import static org.modelmapper.convention.MatchingStrategies.*;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uicheon.userservice.dto.UserDto;
import com.uicheon.userservice.jpa.UserEntity;
import com.uicheon.userservice.service.UserService;
import com.uicheon.userservice.vo.Greeting;
import com.uicheon.userservice.vo.RequestUser;
import com.uicheon.userservice.vo.ResponseUser;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

	private final Environment env;
	private final Greeting greeting;
	private final UserService userService;

	@GetMapping("/health_check")
	@Timed(value = "users.status", longTask = true)
	public String status() {

		return String.format("It's Working in User Service\n"
			+ "\n, port(local.server.port)=" + env.getProperty("local.server.port")
			+ "\n, port(server.port)=" + env.getProperty("server.port")
			+ "\n, token secret=" + env.getProperty("token.secret")
			+ "\n, (token expire time1)=" + env.getProperty("token.expiration_time"));

	}

	@GetMapping("/welcome")
	@Timed(value = "users.status", longTask = true)
	public String welcome() {
		// return env.getProperty("greeting.message");
		return greeting.getMessage();
	}

	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody RequestUser user) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);

		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}

	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers() {
		Iterable<UserEntity> userList = userService.getUserByAll();

		List<ResponseUser> result = new ArrayList<>();
		userList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseUser.class));
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
		UserDto userDto = userService.getUserByUserId(userId);
		ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.OK).body(responseUser);
	}
}

