package com.foodrecepi.linebot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FetchRecipe {
    public String fetchRecipe(String keyword) {
        String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + keyword;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        try {
            JSONObject json = new JSONObject(response.getBody());
            JSONArray meals = json.getJSONArray("meals");

            if (meals.length() == 0) return "Food recipe, Not found " + keyword;

            JSONObject meal = meals.getJSONObject(0);
            String title = meal.getString("strMeal");
            String category = meal.getString("strCategory");
            String instructions = meal.getString("strInstructions");

            return "Title Dish : " + title + "\nCategory " + category + "\n\nHow to cook : \n" + instructions;
        } catch (Exception e) {
            return "Sorry for errors.";
        }
    }

}
