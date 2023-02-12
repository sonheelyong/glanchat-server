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
@Table(name="Alarm_tb")
public class AlarmEntity {

    @Id
    private String email;

    @Column(columnDefinition = "varchar(255) default '0'")
	private String allAlarm;

    @Column(columnDefinition = "varchar(255) default '0'")
	private String followerAlarm;
    
    @Column(columnDefinition = "varchar(255) default '0'")
	private String chatAlarm;

    @Column(columnDefinition = "varchar(255) default '0'")
	private String replyAlarm;
	
    
}
