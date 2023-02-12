package com.green.chat.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.green.chat.model.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, String> {

  //게시글 번호로 다 뽑아내기, nef순으로 정렬
  @Query(value="SELECT * FROM REPLY_TB WHERE BOARD_NUMBER = ? ORDER BY nef", nativeQuery = true)
  List<ReplyEntity> findByBoardNumber(String boardNumber);

  //게시글 번호로 최대 bnum뽑아내기
  @Query(value="SELECT MAX(BNUM) FROM REPLY_TB WHERE BOARD_NUMBER = ?", nativeQuery = true)
  int getMaxBnum(String boardNumber);

  //게시글 번호와 step으로 최대 lvl 뽑아내기
  @Query(value = "SELECT MAX(LVL) FROM REPLY_TB WHERE BOARD_NUMBER =?1 AND STEP = ?2", nativeQuery = true)
  Integer getMaxLvl(String boardNumber, int step);

  //게시글 번호로 최대 nef 뽑아내기
  @Query(value="SELECT MAX(NEF) FROM REPLY_TB WHERE BOARD_NUMBER = ?", nativeQuery = true)
  Integer getMaxNef(String boardNumber);

  //게시글 번호와 step으로 최대 nef 뽑아내기
  @Query(value = "SELECT MAX(NEF) FROM REPLY_TB WHERE BOARD_NUMBER =?1 AND STEP = ?2", nativeQuery = true)
  Integer getMaxNef(String boardNumber, int step);

  //들어온 nef보다 큰 nef값 1씩 증가
  @Transactional
  @Modifying
  @Query(value = "UPDATE REPLY_TB SET NEF = NEF + 1 WHERE NEF > ?", nativeQuery = true)
  void nefPlus(Integer maxNef);

}
