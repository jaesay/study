package com.jaesay.userservice;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    @Timed(value = "users.hello", longTask = true)
    public String hello() {
        return "Hello, user service";
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(request, UserDto.class);
        UserDto savedUserDto = userService.saveNewUser(userDto);
        CreateUserResponse createUserResponse = modelMapper.map(savedUserDto, CreateUserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

    @GetMapping("/{userId}/samples")
    public ResponseEntity<List<SampleResponse>> findMySamples(@PathVariable Long userId) {
        UserDto userDto = userService.findUserByUserId(userId);
        return ResponseEntity.ok(userDto.getSampleResponses());
    }
}
