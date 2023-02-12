package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class HelloMessage {

	private String sendname;
	private String receivename;
	private String cont;
	private String setting;
}
