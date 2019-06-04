package com.cook_ebook.objects;

import java.util.*;

public class Recipe {

    private final int recipeID;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeIngredients;
    private int recipeCookingTime;
    private String recipeImages;
    private RecipeTagSet recipeTagSet;
    private Boolean recipeIsFavourite;
    private Date date;


    public Recipe(final int recipeID) {
        this.recipeID = recipeID;
        this.recipeTitle = null;
        this.recipeDescription = null;
        this.recipeIngredients = null;
        this.recipeCookingTime = -1;
        this.recipeImages = null;
        this.recipeTagSet = null;
        this.recipeIsFavourite = null;
        this.date = new Date(); // Current date
    }

    public Recipe(
        final int recipeID,
        String recipeTitle,
        String recipeDescription,
        String recipeIngredients,
        int recipeCookingTime,
        String recipeImages,
        RecipeTagSet recipeTagSet,
        Boolean recipeIsFavourite,
        Date date
    ) {
        this.recipeID = recipeID;
        this.recipeTitle = recipeTitle;
        this.recipeDescription = recipeDescription;
        this.recipeIngredients = recipeIngredients;
        this.recipeCookingTime = recipeCookingTime;
        this.recipeImages = recipeImages;
        this.recipeTagSet = recipeTagSet;
        this.recipeIsFavourite = recipeIsFavourite;
        this.date = new Date(); // Current date
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public int getRecipeCookingTime() {
        return recipeCookingTime;
    }

    public String getRecipeImages() {
        return recipeImages;
    }

    public Set<String> getRecipeTagSet() {
        return recipeTagSet.getAllTags();
    }

    public Boolean getRecipeIsFavourite() {
        return recipeIsFavourite;
    }

    public Date getRecipeDate() {
        return date;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void setRecipeCookingTime(int recipeCookingTime) {
        this.recipeCookingTime = recipeCookingTime;
    }

    public void setRecipeImages(String recipeImages) {
        this.recipeImages = recipeImages;
    }

    public void setRecipeTags(String newTag, String oldTag) {
        this.recipeTagSet.setTag(newTag, oldTag);
    }

    public void setRecipeIsFavourite(Boolean recipeIsFavourite) {
        this.recipeIsFavourite = recipeIsFavourite;
    }

    public void updateRecipeDate() {
        this.date = new Date(); //update date to current date
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", recipeTitle='" + recipeTitle + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", recipeIngredients='" + recipeIngredients + '\'' +
                ", recipeCookingTime=" + recipeCookingTime +
                ", recipeImages='" + recipeImages + '\'' +
                ", recipeTagSet='" + recipeTagSet.getAllTags() + '\'' +
                ", recipeIsFavourite=" + recipeIsFavourite +
                '}';
    }
}