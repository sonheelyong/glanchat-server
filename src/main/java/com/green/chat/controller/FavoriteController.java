package com.green.chat.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.dto.FavoriteCreateDto;
import com.green.chat.dto.FavoriteScoreDTO;
import com.green.chat.model.FavoriteListEntity;
import com.green.chat.service.FavoriteListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/favoriteList")
public class FavoriteController {

    @Autowired
    FavoriteListService favoriteListService;

    @PutMapping("/algorithm")
    public void algorithm(@AuthenticationPrincipal String email) {
        System.out.println("야 됐냐");
        ProcessBuilder builder;
        BufferedReader br;

        String arg1 = "C:/Users/82105/AppData/Local/Programs/Python/Python38/python.exe";
        String arg2 = "C:/Users/82105/Desktop/ws/chat/chat/src/main/deeplearning/deeplearning_result.py";

        builder = new ProcessBuilder(arg1, arg2, email);

        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();

            int exitval = process.waitFor();

            ArrayList<String> al = new ArrayList<>();

            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr"));
            String line;
        while ((line = br.readLine()) != null) {
            System.out.println(">>>  " + line); // 표준출력에 쓴다
        }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @PostMapping("/create")
    public void create(@RequestBody FavoriteScoreDTO favoriteScoreDTO) {
        System.out.println("나와라");
        System.out.println(favoriteScoreDTO.getEmail());
        FavoriteListEntity favoriteList = FavoriteListEntity.builder()
                .email(favoriteScoreDTO.getEmail())
                .build();

        favoriteListService.save(favoriteList);
    }

    @PutMapping("/plus")
    public void plus(@RequestBody FavoriteCreateDto favoriteCreateDto, @RequestParam String email) {
        for (int i = 0; i <= 2; i++) {
            String text = "";
            switch (i) {
                case 0:
                    text = favoriteCreateDto.getA();
                    break;
                case 1:
                    text = favoriteCreateDto.getB();
                    break;
                case 2:
                    text = favoriteCreateDto.getC();
                    break;
            }

            switch (text) {
                case "game":
                    favoriteListService.update_game(email);
                    break;
                case "music":
                    favoriteListService.update_music(email);
                    break;
                case "movie":
                    favoriteListService.update_movie(email);
                    break;
                case "travel":
                    favoriteListService.update_travel(email);
                    break;
                case "sports":
                    favoriteListService.update_sports(email);
                    break;
                case "food":
                    favoriteListService.update_food(email);
            }
        }
    }

    @GetMapping("/view")
    public Set<String> favoriteView(@AuthenticationPrincipal String email) {
        System.out.println("확인 중");
        System.out.println(email);
        FavoriteListEntity onefavorite = favoriteListService.onefavorite(email);
        System.out.println(onefavorite);

        Integer food = onefavorite.getFood();
        Integer game = onefavorite.getGame();
        Integer movie = onefavorite.getMovie();
        Integer music = onefavorite.getMusic();
        Integer sports = onefavorite.getSports();
        Integer travel = onefavorite.getTravel();

        Map<String, Integer> favoriteCheck = new HashMap<String, Integer>();
        Map<String, List> favoriteCheckList = new HashMap<String, List>();

        favoriteCheck.put("food", food);
        favoriteCheck.put("game", game);
        favoriteCheck.put("movie", movie);
        favoriteCheck.put("music", music);
        favoriteCheck.put("sports", sports);
        favoriteCheck.put("travel", travel);

        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(favoriteCheck.entrySet());
        entryList.sort(((o1, o2) -> favoriteCheck.get(o2.getKey()) - favoriteCheck.get(o1.getKey())));
        List<String> favoritetest = new ArrayList<String>();

        for (int i = 0; i < entryList.size(); i++) {
            favoritetest.add(entryList.get(i).getKey());
            if (favoritetest.size() == 3) {
                favoriteCheckList.put(email, favoritetest);
                break;
            }
        }

        List<FavoriteListEntity> requireFavorite = favoriteListService.requireFavorite(email);
        System.out.println(requireFavorite);
        Map<String, List> recommend_Check = new HashMap<String, List>();
        List<String> checkEmail = new ArrayList<String>();
        for (int i = 0; i < requireFavorite.size(); i++) {
            Map<String, Integer> require_favorite = new HashMap<String, Integer>();

            require_favorite.put("food", requireFavorite.get(i).getFood());
            require_favorite.put("game", requireFavorite.get(i).getGame());
            require_favorite.put("movie", requireFavorite.get(i).getMovie());
            require_favorite.put("music", requireFavorite.get(i).getMusic());
            require_favorite.put("sports", requireFavorite.get(i).getSports());
            require_favorite.put("travel", requireFavorite.get(i).getTravel());

            List<Map.Entry<String, Integer>> require_entryList = new LinkedList<>(require_favorite.entrySet());
            require_entryList.sort(((o1, o2) -> require_favorite.get(o2.getKey()) - require_favorite.get(o1.getKey())));

            List<String> require_favoritetest = new ArrayList<String>();
            checkEmail.add(requireFavorite.get(i).getEmail());

            for (int idex = 0; idex < require_entryList.size(); idex++) {
                require_favoritetest.add(require_entryList.get(idex).getKey());
                if (require_favoritetest.size() == 3) {
                    recommend_Check.put(requireFavorite.get(i).getEmail(), require_favoritetest);
                    break;
                }
            }
        }

        Set<String> last_RecomendEmail = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            String favoriteword = (String) favoriteCheckList.get(email).get(i);
            for (int j = 0; j < checkEmail.size(); j++) {
                String recommendEmail = checkEmail.get(j);
                if (!last_RecomendEmail.contains(recommendEmail)) {
                    if (recommend_Check.get(recommendEmail).contains(favoriteword)) {
                        last_RecomendEmail.add(recommendEmail);
                    }
                }
            }
        }

        return last_RecomendEmail;
    }

}
