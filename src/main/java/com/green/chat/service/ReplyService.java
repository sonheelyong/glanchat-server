package com.green.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.green.chat.model.ReplyEntity;
import com.green.chat.persistence.ReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReplyService {
  
  @Autowired
  private ReplyRepository replyRepository;

//저장
  public void create(ReplyEntity replyEntity) {
    replyRepository.save(replyEntity);
  }

//게시글 번호로 댓글 전부 뽑아서 리스트로 보내기
  public List<ReplyEntity> getreply(String boardNumber) {
    return replyRepository.findByBoardNumber(boardNumber);
  }

  //댓글 아이디로 댓글 하나 뽑기
  public ReplyEntity getById(String id) {
    return replyRepository.getById(id);
  }

  //댓글 하나 삭제
  public void delete(ReplyEntity reply) {
    replyRepository.delete(reply);
  }

  //게시글 번호로 최대 bnum뽑기
  public int getMaxBnum(String boardNumber) {
    return replyRepository.getMaxBnum(boardNumber);
  }

  //게시글 번호와 step으로 최대 lvl뽑아내기
  public Integer getMaxLvl(String boardNumber, int step) {
    return replyRepository.getMaxLvl(boardNumber, step);
  }

  //게시글 번호로 최대 nef뽑아내기
  public Integer getMaxNef(String boardNumber) {
    return replyRepository.getMaxNef(boardNumber);
  }

  //게시글 번호와 step으로 최대 nef 뽑아내기
  public Integer getMaxNef(String boardNumber, int step) {
    return replyRepository.getMaxNef(boardNumber, step);
  }

  //최대 nef로 해당되는 nef 1씩 증가하기
  public void nefPlus(Integer maxNef) {
    replyRepository.nefPlus(maxNef);
  }
}
