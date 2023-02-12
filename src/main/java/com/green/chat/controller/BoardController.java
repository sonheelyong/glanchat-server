package com.green.chat.controller;

import java.util.ArrayList;
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

import com.green.chat.dto.BoardDTO;
import com.green.chat.dto.PageDTO;
import com.green.chat.dto.ResponseDTO;
import com.green.chat.model.BoardEntity;
import com.green.chat.model.UserEntity;
import com.green.chat.service.BoardService;
import com.green.chat.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    // 게시글 작성
    @PostMapping("/write")
    public void write(@AuthenticationPrincipal String email, @RequestBody BoardDTO boardDTO) {
        System.out.println("dd" + boardDTO);

        UserEntity user = userService.findByEmail(email);
        String boardWriter = user.getUsername();

        BoardEntity board = BoardEntity.builder()
                .email(email)
                .boardContent(boardDTO.getBoardContent())
                .boardCategory(boardDTO.getBoardCategory())
                .boardWriter(boardWriter)
                .build();
        boardService.write(board);
    }

    // 게시글 리스트 보기 (페이징 처리)
    @GetMapping("/list/{num}")
    public ResponseEntity<?> boardList(@PathVariable("num") int num) {
        
        int postnum = 10;

        // List<BoardEntity> boardList = boardService.getBoardList();
        List<BoardEntity> boardList = boardService.getBoardListpage(postnum,num);

        if(boardList.size() == 0) {
            ResponseDTO responseDTO = ResponseDTO.builder().error("").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        } else {
            return ResponseEntity.ok(boardList);
        }

    }

    // 게시글 검색 리스트 페이징처리
    @GetMapping("/search/{searchItem}/{num}")
    public ResponseEntity<?> search(@PathVariable("searchItem") String searchItem, @PathVariable("num") int num) {

        int postnum = 10;

        List<BoardEntity> searchList = boardService.search(postnum,num,searchItem);

        return ResponseEntity.ok(searchList);
    }

    // 게시글 검색 리스트
    @GetMapping("/search/{searchItem}")
    public ResponseEntity<?> search(@PathVariable("searchItem") String searchItem) {

        List<BoardEntity> searchList1 = boardService.search1(searchItem);
        List<BoardEntity> searchList2 = boardService.search2(searchItem);
        List<BoardEntity> searchList3 = boardService.search3(searchItem);
       
        System.out.println(searchList1);
        System.out.println(searchList2);
        System.out.println(searchList3);
        List<BoardEntity> result = new ArrayList<>(searchList1);
        result.addAll(searchList2);
        result.addAll(searchList3);
        return ResponseEntity.ok(result);
    }
    
    // 게시글 상세보기
    @GetMapping("/detail/{bno}")
    public ResponseEntity<?> boardDetail(@PathVariable("bno") String bno) {

        BoardEntity boardDetail = boardService.getOneBoardList(bno);

        return ResponseEntity.ok(boardDetail);
    }

    // 게시글 수정
    @PutMapping("/update/{bno}")
    public void update(@PathVariable("bno") String bno, @RequestBody BoardDTO boardDTO) {

        BoardEntity updateList = new BoardEntity();
        updateList = boardService.getOneBoardList(bno);
        updateList.setBoardContent(boardDTO.getBoardContent());

        boardService.write(updateList);
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{bno}")
    public void delete(@PathVariable("bno") String bno) {

        BoardEntity deleteList = new BoardEntity();
        deleteList = boardService.getOneBoardList(bno);

        boardService.delete(deleteList);
    }
    
    // 마이페이지 내가 쓴 게시글 리스트
    @GetMapping("/profile")
    public ResponseEntity<?> profileBoard(@RequestParam("email") String email) {

        System.out.println("hi" + email);
        List<BoardEntity> profileBoard = boardService.profileBoard(email);

        return ResponseEntity.ok(profileBoard);
    }

}
