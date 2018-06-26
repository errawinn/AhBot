package com.example.clair.ahbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodRecipe {
     private String id,title,imagePath;
     private List<String> ingredients;

     List<FoodRecipe> foodRecipes;

     public FoodRecipe(String id, String title, List<String> ingredients, String imagePath){
         this.id = id;
         this.title = title;
         this.ingredients = ingredients;
         this.imagePath = imagePath;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return String.format("https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-img.health.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmarquee_large_2x%2Fpublic%%s",imagePath);

    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    private void initializeData(){
         foodRecipes = new ArrayList<>();
         foodRecipes.add(new FoodRecipe("1", "Smoked Salmon Canapes", Arrays.asList("Salamon","Avocado","Cucumber","Mayonnaise"), "2F1509646895%2Fsmoked-salmon-canapes-champagne-recipes.jpg%3Fitok%3DUpjznAnm&w=300&h=168&c=sc&poi=face&q=85"));
        foodRecipes.add(new FoodRecipe("2", "Roasted Broccoli Salad With Celery and Apple", Arrays.asList("Broccoli","almonds","lemon","red apple"), "2F1508180092%2Froasted-broccoli-salad-celery-apple-recipe.jpg%3Fitok%3DEPWfy4I1&w=300&h=168&c=sc&poi=face&q=85\n"));
    }
}
