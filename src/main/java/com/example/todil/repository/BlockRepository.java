package com.example.todil.repository;

import com.example.todil.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Integer countByIdGreaterThanEqual(Long id);

}
