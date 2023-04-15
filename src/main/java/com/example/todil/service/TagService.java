package com.example.todil.service;

import com.example.todil.domain.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findTags();

    Optional<Tag> findTagByName(String name);

    Tag save(Tag tag);

}
