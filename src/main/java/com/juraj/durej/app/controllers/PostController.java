package com.juraj.durej.app.controllers;

import com.juraj.durej.app.entities.Post;
import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.repositories.PostRepository;
import com.juraj.durej.app.repositories.PostRepository;
import com.juraj.durej.app.services.PostService;
import com.juraj.durej.app.services.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(path = "${api}/posts")
public class PostController {

    private final PostService service;
    private final PostRepository repository;

    public PostController(PostService service, PostRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<List<Post>> allPosts(
            @RequestParam(required = false, name = "filter", defaultValue = "{}") String filterStr,
            @RequestParam(required = false, name = "range", defaultValue = "[0,9]") String rangeStr,
            @RequestParam(required = false, name = "sort", defaultValue = "[\"id\",\"ASC\"]") String sortStr
    ) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range", "posts " + rangeStr.substring( 1, rangeStr.length() - 1 ).replace(",", "-") + "/" + repository.findAll().size());
        responseHeaders.set("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(service.getAllPosts(filterStr, rangeStr, sortStr).getContent());
    }

    @PostMapping("")
    ResponseEntity<Post> createPost(@RequestBody Post newPost) {
        return ResponseEntity.ok()
                .body(service.createPost(newPost));
    }

    @GetMapping("/{id}")
    ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body( service.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return ResponseEntity.ok()
                .body(service.updatePost(id,post));
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }
}