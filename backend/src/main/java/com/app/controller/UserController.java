package com.app.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.domain.user.ForoUser;
import com.app.services.interfaces.IUserService;


@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/user")
public class UserController {

  private IUserService userService;

  public UserController (IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/role") 
  @PreAuthorize("hasRole('ADMIN')")
  public String onlyAdmins () {
    return "Hi you has role ADMIN";
  }

  @GetMapping("/helloworld")
  @PreAuthorize("permitAll()")
  public String helloWorld () {
    return "Hello world !";
  }

  @GetMapping("/check-status")
  @PreAuthorize("hasRole('USER')")
  public ForoUser checkStatus () {
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return userService.getUserByUsername(username);
  }
}  