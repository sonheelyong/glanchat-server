package com.green.chat.controller;

import com.green.chat.dto.LikeDTO;
import com.green.chat.model.LikeEntity;
import com.green.chat.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // 좋아요 증가
    @PostMapping("/increase")
    public void like(
            @AuthenticationPrincipal String email,
            @RequestBody LikeDTO likeDTO) {
        LikeEntity check = likeService.findByBnoAndEmail(likeDTO.getBno(), email);

        if (check == null) {
            LikeEntity entity = LikeEntity
                    .builder()
                    .bno(likeDTO.getBno())
                    .email(email)
                    .build();
            likeService.save(entity);
            System.out.println("좋아요 눌림");
        } 
    }

    // 좋아요 감소
    @PostMapping("/decrease")
    public void unlike(
            @AuthenticationPrincipal String email,
            @RequestBody LikeDTO likeDTO) {
        LikeEntity check = likeService.findByBnoAndEmail(likeDTO.getBno(), email);

        if (check != null) {
            likeService.delete(check);
            System.out.println("좋아요 취소");
        }
    }

    // 좋아요 갯수
    @GetMapping("/count")
    public ResponseEntity<?> likeCnt(@RequestParam String bno) {
        int count = likeService.countByBno(bno);

        return ResponseEntity.ok().body(count);
    }

    // 좋아요 누른 게시글 번호와 email이 db에 있는지 확인
    @GetMapping("/check")
    public ResponseEntity<?> likeCheck(
            @AuthenticationPrincipal String email,
            @RequestParam String bno) {
        LikeEntity check = likeService.findByBnoAndEmail(bno, email);

        if (check == null) {
            return ResponseEntity.ok().body(false);
        } else {
            return ResponseEntity.ok().body(true);
        }
    }
}
