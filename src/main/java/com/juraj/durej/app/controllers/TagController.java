package com.juraj.durej.app.controllers;

import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.TagRepository;
import com.juraj.durej.app.services.TagService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(path = "${api}/tags")
public class TagController {

    private final TagService service;
    private final TagRepository repository;

    TagController(TagService service, TagRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<List<Tag>> allTags(
            @RequestParam(required = false, name = "filter", defaultValue = "{}") String filterStr,
            @RequestParam(required = false, name = "range", defaultValue = "[0,9]") String rangeStr,
            @RequestParam(required = false, name = "sort", defaultValue = "[\"id\",\"ASC\"]") String sortStr
    ) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range", "tags " + rangeStr.substring( 1, rangeStr.length() - 1 ).replace(",", "-") + "/" + repository.findAll().size());
        responseHeaders.set("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(service.getAllTags(filterStr, rangeStr, sortStr).getContent());
    }

    @PostMapping("")
    ResponseEntity<Tag> createTag(@RequestBody Tag newTag) {
        return ResponseEntity.ok()
                .body(service.createTag(newTag));
    }

    @GetMapping("/{id}")
    ResponseEntity<Tag> getTag(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(service.getTagById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        return ResponseEntity.ok()
                .body(service.updateTag(id,tag));
    }

    @DeleteMapping("/{id}")
    void deleteTag(@PathVariable Long id) {
        service.deleteTag(id);
    }
}
