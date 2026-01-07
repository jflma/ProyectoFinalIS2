package com.app.resources;

import org.springframework.stereotype.Component;

import com.app.exceptions.UnauthorizedException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtUtil {

  @Value("${security.jwt.key.private}")
  private String privateKey;

  @Value("${security.jwt.user.generator}")
  private String userGenerator;

  public String generateToken (Authentication authentication){
    Algorithm algorithm = Algorithm.HMAC256(privateKey);

    String username = authentication.getPrincipal().toString();

    String authorities = authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

    return JWT.create()
                      .withIssuer(this.userGenerator)
                      .withSubject(username)
                      .withClaim("authorities", authorities)
                      .withIssuedAt(new Date())
                      .withExpiresAt(new Date(System.currentTimeMillis() + 18000000))
                      .withJWTId(UUID.randomUUID().toString())
                      .withNotBefore(new Date(System.currentTimeMillis()))
                      .sign(algorithm);
  }

  public DecodedJWT validateJWT (String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

      JWTVerifier verifier = JWT.require(algorithm)
                                .withIssuer(this.userGenerator)
                                .build();
      return  verifier.verify(token);
    } catch (Exception ex ) {
      throw new UnauthorizedException("El token no es valido");
    }
  }

  public String extractUsername (DecodedJWT decodedJWT) {
    return decodedJWT.getSubject();
  }

    public Claim getSpecificClaim (DecodedJWT decodedJWT,String claimName) {
    return decodedJWT.getClaim(claimName);
  }

  public Map<String, Claim> returnAllClaims (DecodedJWT decodedJWT) {
    return decodedJWT.getClaims();
  }



}
