package com.app.services.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.controller.dto.response.TokenResponse;
import com.app.domain.user.ForoUser;

public interface IUserService extends UserDetailsService {

  public ForoUser registerUser(SignupFieldsDTO signupFields);

  public ForoUser getUserbyId(Long id);

  public TokenResponse loginUser(LoginRequestDTO loginRequest);

  public Authentication authenticate (String username, String password);

  public ForoUser getUserByUsername (String userName);

}
