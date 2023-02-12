package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FavoriteScoreDTO {
	private String email;
	private String food;
	private String game;
	private String music;
	private String movie;
	private String sports;
	private String travel;

}
