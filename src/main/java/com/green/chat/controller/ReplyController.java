package com.green.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.dto.ReplyDTO;
import com.green.chat.dto.ReplyUpdateDTO;
import com.green.chat.model.ReplyEntity;
import com.green.chat.service.ReplyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reply")
public class ReplyController {

  @Autowired
  private ReplyService replyService;

  // 댓글 생성
  @PostMapping("/register/{boardNumber}")
  public void register(@PathVariable String boardNumber, @AuthenticationPrincipal String user_email,
      @RequestBody ReplyDTO replyDTO) {

    // 댓글 엔티티를 하나 만들기
    ReplyEntity register = null;

    // 댓글이 하나도 없을 경우와 하나라도 있을 경우를 분리
    if (replyService.getreply(boardNumber).size() == 0) {
      System.out.println("첫댓글");
      register = ReplyEntity.builder()
          .email(user_email)
          .boardNumber(boardNumber)
          .replyContent(replyDTO.getReplyContent())
          .build();
    } else {
      // bnum 최댓값 뽑기, 댓글 한 묶음
      int maxbnum = replyService.getMaxBnum(boardNumber);
      // Nef최댓값 뽑기, Nef는 댓글을 출력할 때 쓰기 위한 것
      int maxNef = replyService.getMaxNef(boardNumber);
      // 하나라도 있을 경우에는 bnum과 Nef는 기존에 있던 값에 1씩 추가해서 저장
      int bnum = maxbnum + 1;
      int Nef = maxNef + 1;
      register = ReplyEntity.builder()
          .email(user_email)
          .boardNumber(boardNumber)
          .replyContent(replyDTO.getReplyContent())
          .bnum(bnum)
          .nef(Nef)
          .build();
    }

    // db에 저장
    replyService.create(register);
  }

  // 댓글 출력
  @GetMapping("/request/{boardNumber}")
  public ResponseEntity<?> getreply(@PathVariable String boardNumber) {

    // 해당 게시글에서 모든 댓글을 뽑아내기
    List<ReplyEntity> entities = replyService.getreply(boardNumber);

    // 출력
    return ResponseEntity.ok(entities);
  }

  // 댓글 수정
  @PutMapping("/replyupdate/{id}")
  public void replyUpdate(@AuthenticationPrincipal String user_email, @PathVariable String id, @RequestBody ReplyUpdateDTO replyUpdateDTO) {
    if(user_email != replyUpdateDTO.getEmail()) {
      
    } else {
      // 댓글 기본키로 댓글 뽑아내기
    ReplyEntity reply = replyService.getById(id);

    // 입력한 댓글 내용을 엔티티에 집어넣기
    reply.setReplyContent(replyUpdateDTO.getReplyContent());

    // 수정
    replyService.create(reply);
    }
  }

  // 댓글 삭제
  @DeleteMapping("/delete/{id}")
  public void replyDelete(@PathVariable String id) {

    // 해당 댓글 아이디의 댓글 뽑아내기
    ReplyEntity reply = replyService.getById(id);

    // 댓글 삭제
    replyService.delete(reply);
  }

  // 대댓글 달기
  // 이하 대댓글이 달릴 댓글은 댓글
  // 여기서 생성하는 댓글은 대댓글이라 칭함
  @PostMapping("/replyrequire/{id}")
  public void replyRequire(@PathVariable String id, @AuthenticationPrincipal String user_email,
      @RequestBody ReplyDTO replyDTO) {

    // 댓글의 기본키로 댓글 뽑기
    ReplyEntity reply = replyService.getById(id);

    // 댓글의 step 뽑기, step은 대댓글 달 경우 앞의 띄워지는 칸
    int step = reply.getStep();
    // 대댓글의 최대 lvl을 확인
    Integer maxLvl = replyService.getMaxLvl(reply.getBoardNumber(), step + 1);
    // 대댓글의 최대 nef를 확인
    Integer maxNef = replyService.getMaxNef(reply.getBoardNumber(), step + 1);
    // 댓글의 bnum을 확인
    int bnum = reply.getBnum();

    // 대댓글 step에 들어갈 step
    int requireStep = step + 1;

    // 입력할 댓글엔티티를 초기화
    ReplyEntity register = null;

    // 대댓글의 최대 nef가 없을 경우, 댓글의 nef로 지정
    if (maxNef == null) {
      maxNef = reply.getNef();
    }

    //댓글들의 nef 1씩 증가
    replyService.nefPlus(maxNef);

    //저장할 대댓글의 nef 지정
    maxNef = maxNef + 1;

    // 대댓글의 최대lvl이 있을 경우와 없을 경우로 분리
    if (maxLvl == null) {
      // 대댓글의 lvl이 없을 경우
      register = ReplyEntity.builder()
          .email(user_email)
          .boardNumber(reply.getBoardNumber())
          .replyContent(replyDTO.getReplyContent())
          .bnum(bnum)
          .step(requireStep)
          // nef는 maxnef에 1을 더한 값을 저장
          .nef(maxNef)
          .build();
    } else {
      // 대댓글의 lvl이 있을 경우
      // 대댓글 최대 lvl에 1을 더한 값으로 넣기
      int requireLvl = maxLvl + 1;

      // 엔티티에 넣기
      register = ReplyEntity.builder()
          .email(user_email)
          .boardNumber(reply.getBoardNumber())
          .replyContent(replyDTO.getReplyContent())
          .bnum(bnum)
          .step(requireStep)
          .lvl(requireLvl)
          // nef는 maxnef에 1을 더한 값을 저장
          .nef(maxNef)
          .build();
    }

    // db에 저장
    replyService.create(register);
  }

}
