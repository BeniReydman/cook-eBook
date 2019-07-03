package com.cook_ebook.presentation;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import com.cook_ebook.R;
import com.cook_ebook.logic.RecipeValidator;

public class AddEditView extends AppCompatActivity {

    //This boolean dictates whether a new recipe is being created or being edited
    private static boolean createRecipe;
    private static int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_edit_view);

        //Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //if a recipe needs to be edited rather than created store the bundle containing
        //that recipe and set new recipe accordingly
        Bundle recipe = getIntent().getBundleExtra("recipe_key");
        createRecipe = recipe == null;

        //Fill the textboxes if the recipe is being edited
        if (!createRecipe)
            fillTextBoxes(recipe);
            recipeId = recipe.getInt("recipeID");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_recipe) {
            Intent newRecipe = buildRecipe();
            Bundle extras = newRecipe.getExtras();
            int status = extras.getInt("status");

            if (status == 0) {
                if(!createRecipe){
                    newRecipe.putExtra("update", true);
                    newRecipe.putExtra("recipeID", recipeId);
                }
                setResult(RESULT_OK, newRecipe);
                finish();
            } else {
                String message = "Invalid recipe.";

                if(status == 1)
                    message = "The title of the recipe can't be empty.";
                else if(status == 2)
                    message = "Cooking time has to be a valid number.";
                else if(status == 3)
                    message = "Cooking time can't be a negative number.";

                showErrormessageDialog(message);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //This method fills all the textboxes with the recipe details
    private void fillTextBoxes(Bundle recipe) {
        fillSingleTextBox(R.id.addTitle,recipe.getString("recipeTitle"));
        fillSingleTextBox(R.id.addTime,recipe.getInt("recipeTime")+"");
        fillSingleTextBox(R.id.addDescription,recipe.getString("recipeDescription"));
        fillSingleTextBox(R.id.addIngredients,recipe.getString("recipeIngredients"));
        fillSingleTextBox(R.id.addTags,recipe.getString("recipeTags"));
    }

    //This method fills the a textbox given the textbox's id, and input string
    private void fillSingleTextBox(int id, String text){
        ((EditText)findViewById(id)).setText(text, TextView.BufferType.EDITABLE);
    }

    private boolean validateRecipe(Bundle extras){
        if (extras.getInt("isValid") == 0) {
            return true;
        } else {
            //Display corresponding error message
            if(extras.getInt("isValid") == 1) {
                showErrormessageDialog("Can't entry an empty title!");
            } else if(extras.getInt("isValid") == 2) {
                showErrormessageDialog("Can't entry an non-numeric / empty cooking time!");
            } else if(extras.getInt("isValid") == 3) {
                showErrormessageDialog("Can't entry a non-positive cooking time!");
            }
            return false;
        }
    }

    private void showErrormessageDialog(String errorMessage) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Error message Dialog")
                .setMessage(errorMessage)
                .setNegativeButton("Got it!", null)
                .show();
    }

    private Intent buildRecipe()
    {
        String description =  getDescription();
        String time = getTime();
        String title = getRecipeTitle();
        String tags = getTags();
        String ingredients = getIngredients();

        int status = 0;

        try {
            if (!RecipeValidator.validateTitle(title))
                status = 1;
            else if (!RecipeValidator.validateCookingTimeNumeric(time))
                status = 2;
            else if (!RecipeValidator.validateCookingTimePositive(time))
                status = 3;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return generateIntent(title, description, ingredients, time, tags, status);
    }

    private String getTime()
    {
        EditText textBox = (findViewById(R.id.addTime));
        return textBox.getText().toString();
    }

    private String getDescription()
    {
        EditText textBox = (findViewById(R.id.addDescription));
        return textBox.getText().toString();
    }

    private String getIngredients()
    {
        EditText textBox = (findViewById(R.id.addIngredients));
        return textBox.getText().toString();
    }

    private String getRecipeTitle()
    {
        EditText textBox = (findViewById(R.id.addTitle));
        return textBox.getText().toString();
    }

    private String getTags()
    {
        EditText textBox = (findViewById(R.id.addTags));
        return textBox.getText().toString();
    }

    private Intent generateIntent(String title, String description, String ingredients, String time, String tags, int status)
    {
        Intent myIntent = new Intent();
        myIntent.putExtra("recipeTitle", title);
        myIntent.putExtra("recipeDescription", description);
        myIntent.putExtra("recipeIngredients", ingredients);
        myIntent.putExtra("recipeTime", time);
        myIntent.putExtra("recipeTags", tags);
        myIntent.putExtra("status", status);

        return myIntent;
    }
}