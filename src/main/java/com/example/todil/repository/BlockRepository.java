package com.example.todil.repository;

import com.example.todil.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b FROM Block b WHERE b.user.id = :userId AND b.updateDate >= :startTime AND b.updateDate <= :endTime")
    List<Block> findAllBlocksByUserIdGivenTimeFrame(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    Integer countByIdGreaterThanEqual(Long id);

    Integer countBlocksByUserId(Long user_id);

    @Query("SELECT COUNT(b) FROM Block b WHERE b.user.id = :userId AND b.updateDate >= :startTime AND b.updateDate <= :endTime")
    Integer countBlocksByUserIdGivenTimeFrame(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT b FROM Block b WHERE b.user.id = :user_id ORDER BY ABS(DATEDIFF(b.updateDate, :dateTime)) ASC LIMIT 1")
    Block findClosestBlockByDateAndUserId(@Param("user_id") Long user_id, @Param("dateTime") LocalDate dateTime);

}
