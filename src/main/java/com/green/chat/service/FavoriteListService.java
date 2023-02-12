package com.green.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.chat.model.FavoriteListEntity;
import com.green.chat.persistence.FavoriteRepository;

@Service
public class FavoriteListService {

  @Autowired
    private FavoriteRepository favoriteRepository;

  public void save(FavoriteListEntity favorite) {
    favoriteRepository.save(favorite);

  }

  public FavoriteListEntity getById(String user_email) {
    return favoriteRepository.getById(user_email);
  }

  public FavoriteListEntity onefavorite(String useremail) {
    return favoriteRepository.onefavorite(useremail);
  }

  public void update_game(String email){
    favoriteRepository.update_game(email);
  }

  public void update_music(String email){
    favoriteRepository.update_music(email);
  }

  public void update_movie(String email){
    favoriteRepository.update_movie(email);
  }

  public void update_travel(String email){
    favoriteRepository.update_travel(email);
  }

  public void update_sports(String email){
    favoriteRepository.update_sports(email);
  }

  public void update_food(String email){
    favoriteRepository.update_food(email);
  }

  public List<FavoriteListEntity> requireFavorite(String email) {
    return favoriteRepository.requireFavorite(email);
  }

}
