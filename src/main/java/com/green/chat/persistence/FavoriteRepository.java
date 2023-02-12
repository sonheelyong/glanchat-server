package com.green.chat.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.chat.model.FavoriteListEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteListEntity,String>{

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET GAME = GAME + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_game(String email);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET MUSIC = MUSIC + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_music(String email);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET MOVIE = MOVIE + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_movie(String email);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET TRAVEL = TRAVEL + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_travel(String email);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET SPORTS = SPORTS + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_sports(String email);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE FAVORITE_TB SET FOOD = FOOD + 100 WHERE EMAIL = ?", nativeQuery = true)
  void update_food(String email);

  @Query(value="SELECT * FROM FAVORITE_TB WHERE EMAIL = ?", nativeQuery = true)
  FavoriteListEntity onefavorite(String useremail);

  @Query(value="SELECT * FROM FAVORITE_TB fa WHERE fa.EMAIL IN (SELECT u.EMAIL FROM USER_TB U WHERE EMAIL NOT IN (SELECT l.requireemail FROM USER_TB u INNER JOIN FRIEND_LIST l ON u.EMAIL = l.EMAIL WHERE u.EMAIL = ?1))AND fa.email !=?1", nativeQuery = true)
  List<FavoriteListEntity> requireFavorite(String email);
}
