package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.PostRepository;
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
class PostServiceTest {

    @Mock
    private PostRepository mockRepository;

    private PostService postServiceUnderTest;

    @BeforeEach
    void setUp() {
        postServiceUnderTest = new PostService(mockRepository);
    }

    @Test
    void testCreatePost() {
        // Setup
        final Post post = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));
        final Post expectedResult = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));

        // Configure PostRepository.save(...).
        final Post post1 = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));
        when(mockRepository.save(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))).thenReturn(post1);

        // Run the test
        final Post result = postServiceUnderTest.createPost(post);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllPosts() {
        // Setup
        // Configure PostRepository.findAll(...).
        final Page<Post> posts = new PageImpl<>(
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                        List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())))));
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(posts);

        // Run the test
        final Page<Post> result = postServiceUnderTest.getAllPosts("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetAllPosts_PostRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Post> result = postServiceUnderTest.getAllPosts("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetPostById() {
        // Setup
        final Post expectedResult = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));

        // Configure PostRepository.findById(...).
        final Optional<Post> post = Optional.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))));
        when(mockRepository.findById(0L)).thenReturn(post);

        // Run the test
        final Post result = postServiceUnderTest.getPostById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetPostById_PostRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> postServiceUnderTest.getPostById(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testDeletePost() {
        // Setup
        // Configure PostRepository.findById(...).
        final Optional<Post> post = Optional.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))));
        when(mockRepository.findById(0L)).thenReturn(post);

        // Run the test
        final boolean result = postServiceUnderTest.deletePost(0L);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockRepository).delete(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))));
    }

    @Test
    void testDeletePost_PostRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> postServiceUnderTest.deletePost(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testUpdatePost() {
        // Setup
        final Post post = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));
        final Post expectedResult = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));

        // Configure PostRepository.save(...).
        final Post post1 = new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of())));
        when(mockRepository.save(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))))).thenReturn(post1);

        // Run the test
        final Post result = postServiceUnderTest.updatePost(0L, post);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRepository).save(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()),
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()))));
    }
}
