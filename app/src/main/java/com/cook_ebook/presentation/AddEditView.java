package com.cook_ebook.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import com.cook_ebook.R;

public class AddEditView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_edit_view);

        //Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            setResult(RESULT_OK, newRecipe);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent buildRecipe()
    {
        // get all the pieces
        String description =  getDescription();
        String time = getTime();
        String title = getRecipeTitle();
        String tags = getTags();
        String ingredients = getIngredients();

        // generate a new intent object for the recipe
        return  generateIntent(title, description, ingredients, time, tags);
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

    private Intent generateIntent(String title, String description, String ingredients, String time, String tags)
    {
        Intent myIntent = new Intent();
        myIntent.putExtra("recipeTitle", title);
        myIntent.putExtra("recipeDescription", description);
        myIntent.putExtra("recipeIngredients", ingredients);
        myIntent.putExtra("recipeTime", time);
        myIntent.putExtra("recipeTags", tags);
        return myIntent;
    }
}