package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

  private String id;
  private String email;
  private String boardNumber;
  private String replyContent;
  private String indate;
  private int step;
  private int lvl;
  private int bnum;
  private int nef;
  
}
