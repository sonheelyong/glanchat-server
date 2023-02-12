package com.green.chat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChatFile_tb")
public class ChatFileEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  private String chatid;
  private String filename;
  private String fileOriname;
  private String fileUrl;

  @CreationTimestamp // 입력시 시간 정보를 자동으로 입력해는 어노테이션.
  @Column(name = "insert_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date insertDate;

}
