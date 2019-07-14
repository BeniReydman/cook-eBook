package com.cook_ebook.tests.business;

import com.cook_ebook.logic.RecipeHandler;
import com.cook_ebook.logic.comparators.AscendingTitleComparator;
import com.cook_ebook.logic.comparators.DescendingTitleComparator;
import com.cook_ebook.logic.comparators.LatestDateComparator;
import com.cook_ebook.logic.comparators.OldestDateComparator;
import com.cook_ebook.objects.Recipe;
import com.cook_ebook.objects.RecipeTag;
import com.cook_ebook.tests.utils.TestUtils;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class RecipeHandlerIT {
    private RecipeHandler recipeHandler;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for RecipeHandler");
        this.tempDB = TestUtils.copyDB();
        this.recipeHandler = new RecipeHandler(true);

        assertNotNull(this.recipeHandler);
    }

    @Test
    public void testRecipeList() {
        System.out.println("\nStarting testCreateRecipeList");
        final List<Recipe> recipeList;
        recipeList = recipeHandler.getAllRecipes();

        assertNotNull(recipeList);
        assertEquals(4,recipeList.size());
        for(int i = 0; i < recipeList.size() - 1; i ++) {
            assertTrue(recipeList.get(i).getRecipeDate().after(recipeList.get(i + 1).getRecipeDate())
            || recipeList.get(i).getRecipeDate().equals(recipeList.get(i + 1).getRecipeDate()));
        }

        System.out.println("Finished testCreateRecipeList");
    }

    @Test
    public void testDescendingDateCompare(){
        System.out.println("\nStarting testDescendingDateCompare");
        LatestDateComparator testComparator = new LatestDateComparator();
        final List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        int result = testComparator.compare(actualRecipeList.get(0), actualRecipeList.get(1));
        assertTrue(result <= 0);

        result = testComparator.compare(actualRecipeList.get(1), actualRecipeList.get(2));
        assertTrue(result <= 0);

        result = testComparator.compare(actualRecipeList.get(2), actualRecipeList.get(3));
        assertTrue(result <= 0);

        System.out.println("Finished testDescendingDateCompare");
    }

    @Test
    public void testAscendingDateCompare(){
        System.out.println("\nStarting testAscendingDateCompare");
        OldestDateComparator testComparator = new OldestDateComparator();
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        int result = testComparator.compare(actualRecipeList.get(0), actualRecipeList.get(1));
        assertTrue(result >= 0);

        result = testComparator.compare(actualRecipeList.get(1), actualRecipeList.get(2));
        assertTrue(result >= 0);

        result = testComparator.compare(actualRecipeList.get(2), actualRecipeList.get(3));
        assertTrue(result >= 0);

        System.out.println("Finished testAscendingDateCompare");
    }

    @Test
    public void testDescendingTitleCompare(){
        System.out.println("\nStarting testDescendingTitleCompare");
        DescendingTitleComparator testComparator = new DescendingTitleComparator();
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        int result = testComparator.compare(actualRecipeList.get(0), actualRecipeList.get(1));
        assertTrue(result <= 0);

        System.out.println("Finished testDescendingTitleCompare");
    }

    @Test
    public void testAscendingTitleCompare(){
        System.out.println("\nStarting testAscendingTitleCompare");

        AscendingTitleComparator testComparator = new AscendingTitleComparator();
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        int result = testComparator.compare(actualRecipeList.get(0), actualRecipeList.get(1));
        assertTrue(result >= 0);

        System.out.println("Finished testAscendingTitleCompare");
    }

    @Test
    public void testAscendingDateSort(){
        System.out.println("\nStarting testAscendingDateSort");
        recipeHandler.setSort("Date-Oldest");
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        for(int i = 0; i < actualRecipeList.size() - 1; i++) {
            assertTrue(actualRecipeList.get(i).getRecipeDate().before(actualRecipeList.get(i + 1).getRecipeDate()));
        }

        System.out.println("Finished testAscendingDateSort");
    }

    @Test
    public void testDescendingDateSort(){
        System.out.println("\nStarting testDescendingDateSort");
        recipeHandler.setSort("Date-Latest");
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        for(int i = 0; i < actualRecipeList.size() - 1; i++) {
            assertTrue(actualRecipeList.get(i).getRecipeDate().after(actualRecipeList.get(i + 1).getRecipeDate()));
        }

        System.out.println("Finished testDescendingDateSort");
    }

    @Test
    public void testAscendingTitleSort(){
        System.out.println("\nStarting testAscendingTitleSort");
        recipeHandler.setSort("Title-Ascending");
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        for(int i = 1; i < actualRecipeList.size(); i++) {
            Recipe r1 = actualRecipeList.get(i-1);
            Recipe r2 = actualRecipeList.get(i);
            String s1 = r1.getRecipeTitle();
            String s2 = r2.getRecipeTitle();
            assertTrue(s1.compareTo(s2) < 0 || s1.equals(s2));
        }

        System.out.println("Finished testAscendingTitleSort");
    }

    @Test
    public void testDescendingTitleSort(){
        System.out.println("\nStarting testDescendingTitleSort");
        recipeHandler.setSort("Title-Descending");
        List<Recipe> actualRecipeList = recipeHandler.getAllRecipes();

        for(int i = 1; i < actualRecipeList.size(); i++) {
            Recipe r1 = actualRecipeList.get(i-1);
            Recipe r2 = actualRecipeList.get(i);
            String s1 = r1.getRecipeTitle();
            String s2 = r2.getRecipeTitle();
            assertTrue(s1.compareTo(s2) > 0 || s1.equals(s2));
        }

        System.out.println("Finished testDescendingTitleSort");
    }

    @Test
    public void testGetRecipeById() {
        System.out.println("\nStarting testGetRecipeById");
        Recipe targetRecipe = recipeHandler.getRecipeById(1);

        assertEquals("Chocolate Chip Cookies", targetRecipe.getRecipeTitle());
        assertEquals("Crisp edges, chewy middles, and so, so easy to make. Try this wildly-popular " +
                "chocolate chip cookie recipe for yourself.", targetRecipe.getRecipeDescription());
        assertEquals("1/4 cups of all purpose flour. 1 tsp baking soda. 1/2 cup of butter. 1 tsp salt ( " +
                "use 1/2 tsp if you are using salted butter) 1 cup of packed brown sugar. 1/2 cup of granulated sugar. " +
                "1 1/2 tsp vanilla. 2 eggs.", targetRecipe.getRecipeIngredients());
        assertEquals(30, targetRecipe.getRecipeCookingTime());
        assertEquals("/add/path/to/resource/later", targetRecipe.getRecipeImage());
        assertEquals("2019-02-14 02:30:00.0", targetRecipe.getRecipeDate().toString());

        System.out.println("Finished testGetRecipeById");
    }

    @Test
    public void testResetFilter() {
        System.out.println("\nStarting testResetFilter");
        recipeHandler.resetFilter();
        int length = recipeHandler.getFilter().size();
        assertEquals(0, length);

        System.out.println("Finished testResetFilter");
    }

    @Test
    public void testResetSort() {
        System.out.println("\nStarting testResetSort");
        recipeHandler.resetSort();
        assertTrue(recipeHandler.getSort() instanceof LatestDateComparator);
        System.out.println("Finished testResetSort");
    }

    @Test
    public void testResetFavourite() {
        System.out.println("\nStarting testResetFavourite");
        recipeHandler.resetFavourite();
        assertFalse(recipeHandler.getFavourite());
        System.out.println("Finished testResetFavourite");
    }

    @Test
    public void testSetFavourite() {
        System.out.println("\nStarting testSetFavourite");
        recipeHandler.setFavourite(true);
        assertTrue(recipeHandler.getFavourite());

        List<Recipe> recipeList = recipeHandler.getAllRecipes();
        for (Recipe recipe: recipeList) {
            assertTrue(recipe.getRecipeIsFavourite());
        }

        System.out.println("Finished testSetFavourite");
    }

    @Test
    public void testSetFilter() {
        System.out.println("\nStarting testSetFilter");
        recipeHandler.setFilter("meal");
        int length = recipeHandler.getFilter().size();
        assertEquals(1, length);

        recipeHandler.setFilter("dessert");
        length = recipeHandler.getFilter().size();
        assertEquals(2, length);

        recipeHandler.setFilter("salty");
        length = recipeHandler.getFilter().size();
        assertEquals(3, length);

        recipeHandler.setFilter("savoury");
        length = recipeHandler.getFilter().size();
        assertEquals(4, length);

        recipeHandler.setFilter("breakfast");
        length = recipeHandler.getFilter().size();
        assertEquals(5, length);

        System.out.println("Finished testSetFilter");
    }

    @Test
    public void testFilter() {
        System.out.println("\nStarting testFilter");
        String [] tags = {"meal", "salty"};
        boolean [] checked = {true, false};
        RecipeTag newTag = new RecipeTag("salty");
        recipeHandler.filter(tags, checked);
        List <Recipe> recipeList = recipeHandler.getAllRecipes();
        for (Recipe recipe: recipeList) {
            List<RecipeTag> tagList = recipe.getRecipeTagList();
            assertTrue(tagList.contains(newTag));
        }

        System.out.println("Finished testFilter");
    }

    @Test
    public void testSetSort() {
        System.out.println("\nStarting testSetSort");
        recipeHandler.setSort("Date-Oldest");
        assertTrue(recipeHandler.getSort() instanceof OldestDateComparator);

        recipeHandler.setSort("Date-Latest");
        assertTrue(recipeHandler.getSort() instanceof LatestDateComparator);

        recipeHandler.setSort("Title-Ascending");
        assertTrue(recipeHandler.getSort() instanceof AscendingTitleComparator);

        recipeHandler.setSort("Title-Descending");
        assertTrue(recipeHandler.getSort() instanceof DescendingTitleComparator);

        System.out.println("Finished testSetSort");
    }

    @Test
    public void testGetRecipeListByCookingTime() {
        System.out.println("\nStarting testGetRecipeListByCookingTime");
        List<Recipe> actualRecipeList = recipeHandler.getRecipeListByCookingTime(30);

        assertEquals(1, actualRecipeList.size());

        for(int i = 0; i < actualRecipeList.size(); i ++) {
            assertEquals(30, actualRecipeList.get(i).getRecipeCookingTime());
        }

        System.out.println("Finished testGetRecipeListByCookingTime");
    }

    @Test
    public void testGetRecipeListByTagName() {
        System.out.println("\nStarting testGetRecipeListByTagName");
        List<Recipe> actualRecipeList1 = recipeHandler.getRecipeListByTagName("meal");
        assertEquals(3, actualRecipeList1.size());

        List<Recipe> actualRecipeList2 = recipeHandler.getRecipeListByTagName("dessert");
        assertEquals(1, actualRecipeList2.size());

        System.out.println("Finished testGetRecipeListByTagName");
    }

    @Test
    public void testGetRecipeListByTagId() {
        System.out.println("\nStarting testGetRecipeListByTagId");

        List<Recipe> actualRecipeList1 = recipeHandler.getRecipeListByTagId(-1);
        assertEquals(0, actualRecipeList1.size());

        // need more tests


        System.out.println("Finished testGetRecipeListByTagId");
    }

    @Test
    public void testGetRecipeListByTag() {
        System.out.println("\nStarting testGetRecipeListByTag");
        List<Recipe> actualRecipeList1 = recipeHandler.getRecipeListByTag(new RecipeTag("cake"));
        assertEquals(1, actualRecipeList1.size());

        List<Recipe> actualRecipeList2 = recipeHandler.getRecipeListByTag(new RecipeTag("dessert"));
        assertEquals(2, actualRecipeList2.size());

        System.out.println("Finished testGetRecipeListByTag");
    }

//    @Test
//    public void testGetRecipeListByFavourite() {
//        System.out.println("\nStarting testGetRecipeListByFavourite");
//
//        System.out.println("Finished testGetRecipeListByFavourite");
//    }
//
//    @Test
//    public void testInsertRecipe() {
//        System.out.println("\nStarting testInsertRecipe");
//
//
//
//        System.out.println("Finished testInsertRecipe");
//    }
//
//    @Test
//    public void testUpdateRecipe() {
//        System.out.println("\nStarting testUpdateRecipe");
//
//
//
//        System.out.println("Finished testUpdateRecipe");
//    }
//
//    @Test
//    public void testDeleteRecipe() {
//        System.out.println("\nStarting testDeleteRecipe");
//
//
//
//        System.out.println("Finished testDeleteRecipe");
//    }
//
//    @Test
//    public void testDeleteRecipeById() {
//        System.out.println("\nStarting testDeleteRecipeById");
//
//
//
//        System.out.println("Finished testDeleteRecipeById");
//    }

    @After
    public void tearDown() {
        System.out.println("Reset database.");
        // reset DB
        this.tempDB.delete();
    }
}