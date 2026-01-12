package com.app.modules.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.controller.dto.response.TokenResponse;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Person;
import com.app.modules.user.domain.Role;
import com.app.modules.user.persistence.UserRepositoryImp;
import com.app.modules.auth.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private IPersonService personService;
  @Mock
  private UserRepositoryImp userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private UserService userService;

  @Test
  void testRegisterUser() {
    LocalDate birthday = LocalDate.now();
    String firstName = "nombre1";
    String lastName = "nombre2";
    String userName = "username";
    String email = "email@google.com";
    String password = "password";

    Person person = new Person();
    person.setEmail(email);

    SignupFieldsDTO fields = new SignupFieldsDTO(firstName, lastName, email, birthday, userName, password);

    ForoUser user = new ForoUser();
    user.setUsername(userName);
    user.setPerson(person);

    // when
    when(personService.createPerson(firstName, lastName, email, birthday)).thenReturn(person);
    when(userRepository.save(any(ForoUser.class))).thenReturn(user);

    // act
    ForoUser userCreated = userService.registerUser(fields);

    // assert
    assertNotNull(userCreated);
    assertEquals(user.getUsername(), userCreated.getUsername());

  }

  @Test
  void testGetUserbyId() {
    Long id = 1L;
    ForoUser user = new ForoUser();
    user.setId(id);

    // when
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    // act
    ForoUser userFound = userService.getUserbyId(id);

    // assert
    assertNotNull(userFound);
    assertEquals(id, userFound.getId());
  }

  @Test
  void testGetUserByUsername() {
    String username = "username";
    ForoUser user = new ForoUser();
    user.setUsername(username);

    // when
    when(userRepository.findForoUserByUsername(username)).thenReturn(Optional.of(user));

    ForoUser userFound = userService.getUserByUsername(username);

    assertNotNull(userFound);
    assertEquals(username, userFound.getUsername());
  }

  @Test
  void testLoadUserByUsername() {
    String username = "test1";
    String password = "password";

    ForoUser user = new ForoUser();
    Role admin = new Role();
    admin.setName("ADMIN");
    user.setUsername(username);
    user.setPassword(password);
    user.setRoles(Set.of(admin));

    when(userRepository.findForoUserByUsername(username)).thenReturn(Optional.of(user));

    UserDetails userDetails = userService.loadUserByUsername(username);

    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
  }

  @Test
  void testAuthenticate() {
    String username = "test1";
    String password = "password";
    String role = "ADMIN";

    ForoUser user = new ForoUser();
    Role admin = new Role();
    admin.setName("ADMIN");
    user.setUsername(username);
    user.setPassword(password);
    user.setRoles(Set.of(admin));

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role)));

    when(userRepository.findForoUserByUsername(username)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, password)).thenReturn(true);

    Authentication userSucessfully = userService.authenticate(username, password);

    assertNotNull(userSucessfully);

  }

  @Test
  void testLoginUser() {
    String username = "test1";
    String password = "password";
    String token = "token";

    ForoUser user = new ForoUser();
    Role admin = new Role();
    admin.setName("ADMIN");
    user.setUsername(username);
    user.setPassword(password);
    user.setRoles(Set.of(admin));

    LoginRequestDTO fields = new LoginRequestDTO(username, password);

    when(userRepository.findForoUserByUsername(username)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, password)).thenReturn(true);
    when(jwtUtil.generateToken(any(Authentication.class))).thenReturn(token);

    TokenResponse jwt = userService.loginUser(fields);

    assertNotNull(jwt);
    assertEquals(token, jwt.getToken());
  }
}
