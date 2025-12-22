package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.controller.dto.response.LoginResponse;
import com.app.domain.user.ForoUser;
import com.app.services.interfaces.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
  public String test() {
    return "Endpoint /auth run!";
  }

  @PostMapping("/signup")
  public ForoUser signupUser(@RequestBody SignupFieldsDTO signupFields) {
    return userService.registerUser(signupFields);
  }

  @PostMapping("/signin")
  public LoginResponse loginUser(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
    // Autenticar usuario
    Authentication auth = userService.authenticate(loginRequest.username(), loginRequest.password());

    // Crear sesión y guardar atributos del usuario
    HttpSession session = request.getSession(true);
    session.setAttribute("username", auth.getName());
    session.setAttribute("authorities", auth.getAuthorities());
    session.setAttribute("isAuthenticated", true);

    return LoginResponse.builder()
        .message("Login exitoso")
        .username(auth.getName())
        .build();
  }

  @PostMapping("/logout")
  public LoginResponse logoutUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return LoginResponse.builder()
        .message("Logout exitoso")
        .username(null)
        .build();
  }

}
