package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository);
    }

    @Test
    void testCreateUser() {
        // Setup
        final User user = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        final User expectedResult = new User(0L, "name", "email", "username", "password",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));

        // Configure UserRepository.save(...).
        final User user1 = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockUserRepository.save(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))))))
                .thenReturn(user1);

        // Run the test
        final User result = userServiceUnderTest.createUser(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockUserRepository).save(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
    }

    @Test
    void testGetAllUsers() {
        // Setup
        // Configure UserRepository.findAll(...).
        final Page<User> users = new PageImpl<>(
                List.of(new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))))));
        when(mockUserRepository.findAll(any(Pageable.class))).thenReturn(users);

        // Run the test
        final Page<User> result = userServiceUnderTest.getAllUsers("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetAllUsers_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<User> result = userServiceUnderTest.getAllUsers("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetUserById() {
        // Setup
        final User expectedResult = new User(0L, "name", "email", "username", "password",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.getUserById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.getUserById(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testDeleteUser() {
        // Setup
        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final boolean result = userServiceUnderTest.deleteUser(0L);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockUserRepository).delete(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
    }

    @Test
    void testDeleteUser_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.deleteUser(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testUpdateUser() {
        // Setup
        final User user = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        final User expectedResult = new User(0L, "name", "email", "username", "password",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));

        // Configure UserRepository.save(...).
        final User user1 = new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockUserRepository.save(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))))))
                .thenReturn(user1);

        // Run the test
        final User result = userServiceUnderTest.updateUser(0L, user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockUserRepository).save(
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, null,
                                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))));
    }
}
