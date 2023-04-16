package com.example.todil.repository;

import com.example.todil.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Integer countByIdGreaterThanEqual(Long id);

    Integer countBlocksByUserId(Long user_id);

    @Query("SELECT b FROM Block b WHERE b.user.id = :user_id ORDER BY ABS(DATEDIFF(b.updateDate, :dateTime)) ASC LIMIT 1")
    Block findClosestBlockByDateAndUserId(@Param("user_id") Long user_id, @Param("dateTime") LocalDate dateTime);

}
