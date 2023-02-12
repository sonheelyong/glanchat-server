package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardDTO {

    private String bno;
    private String boardContent;
    private String boardWriter;
    private String boardHashTag;
    private String boardCategory;

}
