package com.juraj.durej.app.controllers;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.UserRepository;
import com.juraj.durej.app.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Disabled
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockService;
    @MockBean
    private UserRepository mockUserRepository;

    @Test
    void testAllUsers() throws Exception {
        // Setup
        // Configure UserRepository.findAll(...).
        final List<User> users = List.of(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
        when(mockUserRepository.findAll()).thenReturn(users);

        // Configure UserService.getAllUsers(...).
        final Page<User> users1 = new PageImpl<>(
                List.of(new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))))));
        when(mockService.getAllUsers("filterStr", "rangeStr", "sortStr")).thenReturn(users1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1API/users")
                        .param("filter", "{}")
                        .param("range", "[0,9]")
                        .param("sort", "[\"id\",\"ASC\"]")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAllUsers_UserRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Configure UserService.getAllUsers(...).
        final Page<User> users = new PageImpl<>(
                List.of(new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))))));
        when(mockService.getAllUsers("filterStr", "rangeStr", "sortStr")).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1API/users")
                        .param("filter", "{}")
                        .param("range", "[0,9]")
                        .param("sort", "[\"id\",\"ASC\"]")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAllUsers_UserServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure UserRepository.findAll(...).
        final List<User> users = List.of(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
        when(mockUserRepository.findAll()).thenReturn(users);

        when(mockService.getAllUsers("filterStr", "rangeStr", "sortStr"))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1API/users/1")
                        .param("filter", "filterStr")
                        .param("range", "rangeStr")
                        .param("sort", "sortStr")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testCreateUser() throws Exception {
        // Setup
        // Configure UserService.createUser(...).
        final User user = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockService.createUser(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))))).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1API/users")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final User user = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1API/users/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateUser() throws Exception {
        // Setup
        // Configure UserService.updateUser(...).
        final User user = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockService.updateUser(0L,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))))).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/v1API/users/{userId}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup
        when(mockService.deleteUser(0L)).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/v1API/users/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockService).deleteUser(0L);
    }
}
