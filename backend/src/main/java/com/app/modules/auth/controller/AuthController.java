package com.app.modules.auth.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TokenResponseDTO;
import com.app.dto.UserResponseDTO;
import com.app.modules.auth.controller.dto.LoginRequestDTO;
import com.app.modules.auth.controller.dto.SignupFieldsDTO;
import com.app.modules.auth.controller.dto.response.TokenResponse;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Role;
import com.app.modules.user.service.IUserService;

@RestController
@CrossOrigin("http://localhost:3000/")
@PreAuthorize("permitAll")
@RequestMapping("/auth")
public class AuthController {

  private IUserService userService;

  public AuthController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/hello")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Endpoint /auth run!");
  }

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDTO> signupUser(@RequestBody SignupFieldsDTO signupFields) {
    ForoUser user = userService.registerUser(signupFields);
    UserResponseDTO response = UserResponseDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getPerson().getEmail())
        .firstName(user.getPerson().getFirstName())
        .lastName(user.getPerson().getLastName())
        .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/signin")
  public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest) {
    TokenResponse tokenResponse = userService.loginUser(loginRequest);
    TokenResponseDTO response = TokenResponseDTO.builder()
        .token(tokenResponse.getToken())
        .build();
    return ResponseEntity.ok(response);
  }

}
