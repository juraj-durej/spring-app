package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.Tag;
import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.TagRepository;
import com.juraj.durej.app.repositories.TagRepository;
import com.juraj.durej.app.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public Tag createTag(Tag tag) {
        repository.save(tag);
        return tag;
    }

    public Page<Tag> getAllTags(String filterStr, String rangeStr, String sortStr) {

        Pageable sorted = SortUtils.getPage(filterStr, rangeStr, sortStr);

        return repository.findAll(sorted);
    }

    public Tag getTagById(Long id) {
        return repository.findById(id).get();
    }

    public boolean deleteTag(Long id) {
        Tag tag =  repository.findById(id).get();
        repository.delete(tag);
        return true;
    }

    public Tag updateTag(Long id, Tag tag) {
        repository.save(tag);
        return tag;
    }
}
