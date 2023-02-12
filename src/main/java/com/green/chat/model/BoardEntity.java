package com.green.chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Board_tb")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String bno;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Lob
    private String boardContent;

    @Column(nullable = false)
    private String boardWriter;

    @Column(nullable = true)
    private String boardCategory;

    @Column(nullable = true)
    private String boardHashTag;

    @Column(nullable = true)
    private int fileNumber;

    @Column(columnDefinition = "Number default 0")
    private int boardLike;

}
