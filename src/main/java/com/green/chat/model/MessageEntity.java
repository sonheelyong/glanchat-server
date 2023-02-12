package com.green.chat.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Message_tb")
@SequenceGenerator(
            name="USER_SEQ_GEN", //시퀀스 제너레이터 이름
            sequenceName="USER_SEQ", //시퀀스 이름
            initialValue=1, //시작값
            allocationSize=1 //메모리를 통해 할당할 범위 사이즈
            )
public class MessageEntity {
    
    @Id
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="USER_SEQ_GEN" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정        
            )
    private Long id;


    @Column(nullable = false)
    private String senderName;
   
    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String messagedate;

    @Column(nullable = false)
    private String mesasagestatus;

    @Column(nullable = true, columnDefinition = "varchar(255) default '0'")
    private String message_check;



    

}
