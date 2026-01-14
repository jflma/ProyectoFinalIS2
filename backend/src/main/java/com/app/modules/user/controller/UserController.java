package com.app.modules.user.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserResponseDTO;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Role;
import com.app.modules.user.service.IUserService;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/user")
public class UserController {

  private IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> onlyAdmins() {
    return ResponseEntity.ok("Hi you has role ADMIN");
  }

  @GetMapping("/helloworld")
  @PreAuthorize("permitAll()")
  public ResponseEntity<String> helloWorld() {
    return ResponseEntity.ok("Hello world !");
  }

  @GetMapping("/check-status")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<UserResponseDTO> checkStatus() {
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    ForoUser user = userService.getUserByUsername(username);

    UserResponseDTO response = UserResponseDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getPerson().getEmail())
        .firstName(user.getPerson().getFirstName())
        .lastName(user.getPerson().getLastName())
        .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
        .build();

    return ResponseEntity.ok(response);
  }
}