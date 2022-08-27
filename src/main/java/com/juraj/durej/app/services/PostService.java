package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.repositories.PostRepository;
import com.juraj.durej.app.repositories.PostRepository;
import com.juraj.durej.app.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Post createPost(Post post) {
        post = repository.save(post);
        return post;
    }

    public Page<Post> getAllPosts(String filterStr, String rangeStr, String sortStr) {

        Pageable sorted = SortUtils.getPage(filterStr, rangeStr, sortStr);

        return repository.findAll(sorted);
    }
    public Post getPostById(Long id) {
        return repository.findById(id).get();
    }

    public boolean deletePost(Long id) {
        Post post =  repository.findById(id).get();
        repository.delete(post);
        return true;
    }

    public Post updatePost(Long id, Post post) {
        repository.save(post);
        return post;
    }
}
