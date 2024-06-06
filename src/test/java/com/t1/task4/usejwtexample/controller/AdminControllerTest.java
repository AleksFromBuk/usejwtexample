package com.t1.task4.usejwtexample.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER") // Прокси пользователь с ролью USER
    public void test_admin_endpoint_returns_403_for_non_admin() throws Exception {
        mockMvc.perform(get("/admin")) // Выполняем GET-запрос к /admin
                .andExpect(status().isForbidden()); // Ожидаем статус 403 (Forbidden)
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Прокси администратора с ролью ADMIN
    public void test_admin_endpoint_returns_admin_for_role_admin() throws Exception {
        mockMvc.perform(get("/admin")) // Выполняем GET-запрос к /admin
                .andExpect(status().isOk()); // Ожидаем статус 200 (OK), так как администратор имеет доступ к этому эндпоинту
    }
}
