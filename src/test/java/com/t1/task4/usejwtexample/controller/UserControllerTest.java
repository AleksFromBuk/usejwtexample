package com.t1.task4.usejwtexample.controller;

import com.t1.task4.usejwtexample.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(SecurityConfiguration.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_user_with_role_user_can_access() {
        UserController userController = new UserController();
        String result = userController.user().block();
        assertEquals("User", result);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void test_admin_endpoint_returns_admin_for_role_admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_unauthenticated_user_is_denied_access() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ANONYMOUS")
    public void test_anonymous_user_is_denied_access() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "INVALID_ROLE")
    public void test_invalid_role_is_denied_access() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }

}
