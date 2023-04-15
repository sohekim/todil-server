package com.example.todil.service;

import com.example.todil.domain.entity.Tag;
import com.example.todil.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    final TagRepository tagRepository;

    @Override
    public List<Tag> findTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
