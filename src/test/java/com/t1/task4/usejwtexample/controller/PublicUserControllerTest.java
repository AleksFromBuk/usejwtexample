package com.t1.task4.usejwtexample.controller;

import com.t1.task4.usejwtexample.configuration.SecurityConfiguration;
import com.t1.task4.usejwtexample.dto.CreateUserRequest;
import com.t1.task4.usejwtexample.entity.User;
import com.t1.task4.usejwtexample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(SecurityConfiguration.class)
public class PublicUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PublicUserController publicUserController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publicUserController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setUsername("testUser");
        request.setRoles(Set.of("ROLE_USER"));

        User mockUser = new User();
        when(userService.create(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/v1/public/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password\", \"username\":\"testUser\", \"roles\":[\"ROLE_USER\"]}"))
                .andExpect(status().isOk());
    }
}
