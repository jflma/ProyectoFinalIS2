package com.app.security;

import com.app.modules.auth.controller.dto.LoginRequestDTO;
import com.app.modules.auth.controller.dto.SignupFieldsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de seguridad para validar que el sistema rechace correctamente
 * accesos no autorizados
 * y proteja sus endpoints críticos.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUnauthorizedAccessToProtectedEndpoint() throws Exception {
        // Intentar crear un post sin autenticación debe fallar con 401
        // Nota: IdUser es ignorado en el servicio que usa SecurityContext, pero el DTO
        // lo requiere
        // CreatePostFieldsDTO(Long idUser, String title, String content)
        String postJson = """
                {
                    "idUser": 1,
                    "title": "Test Post",
                    "content": "Test Content"
                }
                """;

        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testPublicEndpointAccessibleWithoutAuth() throws Exception {
        // El registro de nuevos usuarios debe ser accesible sin autenticación
        SignupFieldsDTO newUser = new SignupFieldsDTO(
                "Test",
                "User",
                "securitytest" + System.currentTimeMillis() + "@test.com",
                java.time.LocalDate.parse("1990-01-01"),
                "secuser" + System.currentTimeMillis(),
                "password123");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetRecentPostsIsPublic() throws Exception {
        // El endpoint de posts recientes debe ser público
        mockMvc.perform(get("/post/ultimatePost"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() throws Exception {
        // Credenciales incorrectas deben ser rechazadas
        // Endpoint real: /auth/signin
        LoginRequestDTO invalidLogin = new LoginRequestDTO("nonexistent", "wrongpassword");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLogin)))
                // Expect 403 Forbidden or 401 Unauthorized or 404/400 depending on handler
                // Custom exception handler might return 401 or 403
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testXSSAttemptDoesNotBreakSystem() throws Exception {
        // El sistema debe manejar inputs con código JavaScript sin romperse
        SignupFieldsDTO xssAttempt = new SignupFieldsDTO(
                "<script>alert('XSS')</script>",
                "User",
                "xsstest" + System.currentTimeMillis() + "@test.com",
                java.time.LocalDate.parse("1990-01-01"),
                "xssuser" + System.currentTimeMillis(),
                "password123");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(xssAttempt)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testMalformedJSONIsRejected() throws Exception {
        // JSON malformado debe ser rechazado con 400 Bad Request
        String malformedJson = "{\"username\": invalid}";

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
    }
}
