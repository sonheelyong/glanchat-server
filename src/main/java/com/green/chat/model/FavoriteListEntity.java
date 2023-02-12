package com.green.chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

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
@Table(name="FAVORITE_tb")
public class FavoriteListEntity {

  @Id
  private String email;

  @Column(columnDefinition = "Number default 0")
  private Integer food;

  @Column(columnDefinition = "Number default 0")
	private Integer game;

  @Column(columnDefinition = "Number default 0")
	private Integer movie;

  @Column(columnDefinition = "Number default 0")
	private Integer music;

  @Column(columnDefinition = "Number default 0")
	private Integer sports;

  @Column(columnDefinition = "Number default 0")
	private Integer travel;
  
}
