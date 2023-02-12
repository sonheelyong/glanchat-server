package com.green.chat.persistence;

import org.springframework.stereotype.Repository;

import com.green.chat.model.BoardEntity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    BoardEntity findByBno(String bno);

    BoardEntity getByBno(String bno);

    List<BoardEntity> findByBoardContentContaining(String searchItem);
    
    List<BoardEntity> findByBoardCategoryContaining(String result);
    
    List<BoardEntity> findByBoardHashTagContaining(String searchItem);

    List<BoardEntity> findByBoardContentContaining(String searchItem, Pageable pageable);
    
    @Query(value = "SELECT b FROM BoardEntity b")
    List<BoardEntity> getBoardListpage(Pageable pageable);

    List<BoardEntity> findByEmail(String email);

}
