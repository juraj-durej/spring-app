package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.TagRepository;
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
class TagServiceTest {

    @Mock
    private TagRepository mockRepository;

    private TagService tagServiceUnderTest;

    @BeforeEach
    void setUp() {
        tagServiceUnderTest = new TagService(mockRepository);
    }

    @Test
    void testCreateTag() {
        // Setup
        final Tag tag = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));
        final Tag expectedResult = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));

        // Configure TagRepository.save(...).
        final Tag tag1 = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));
        when(mockRepository.save(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())))))
                .thenReturn(tag1);

        // Run the test
        final Tag result = tagServiceUnderTest.createTag(tag);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRepository).save(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of()))));
    }

    @Test
    void testGetAllTags() {
        // Setup
        // Configure TagRepository.findAll(...).
        final Page<Tag> tags = new PageImpl<>(
                List.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                                new User(0L, "name", "email", "username", "password",
                                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                        false, List.of()), List.of())))));
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(tags);

        // Run the test
        final Page<Tag> result = tagServiceUnderTest.getAllTags("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetAllTags_TagRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Tag> result = tagServiceUnderTest.getAllTags("{}", "[0,9]", "[\"id\",\"ASC\"]");

        // Verify the results
    }

    @Test
    void testGetTagById() {
        // Setup
        final Tag expectedResult = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));

        // Configure TagRepository.findById(...).
        final Optional<Tag> tag = Optional.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of()))));
        when(mockRepository.findById(0L)).thenReturn(tag);

        // Run the test
        final Tag result = tagServiceUnderTest.getTagById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTagById_TagRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> tagServiceUnderTest.getTagById(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testDeleteTag() {
        // Setup
        // Configure TagRepository.findById(...).
        final Optional<Tag> tag = Optional.of(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of()))));
        when(mockRepository.findById(0L)).thenReturn(tag);

        // Run the test
        final boolean result = tagServiceUnderTest.deleteTag(0L);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockRepository).delete(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of()))));
    }

    @Test
    void testDeleteTag_TagRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> tagServiceUnderTest.deleteTag(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testUpdateTag() {
        // Setup
        final Tag tag = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));
        final Tag expectedResult = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));

        // Configure TagRepository.save(...).
        final Tag tag1 = new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())));
        when(mockRepository.save(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of())))))
                .thenReturn(tag1);

        // Run the test
        final Tag result = tagServiceUnderTest.updateTag(0L, tag);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRepository).save(new Tag(0L, "title", "tag", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                List.of(new Post(0L, "title", "content", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new User(0L, "name", "email", "username", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, List.of()), List.of()))));
    }
}
