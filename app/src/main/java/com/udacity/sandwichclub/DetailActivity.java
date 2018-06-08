package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTextView;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsImageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        List<String> ingredientsList = sandwich.getIngredients();

        // Check if the sandwich is known with more than 1 names
        // If yes, append the extra names separated with comma
        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            if (i != 0) {
                alsoKnownAsTextView.append(", ");
            }
            alsoKnownAsTextView.append(alsoKnownAsList.get(i));
        }

        // Check if the sandwich is made up of more than 1 ingredients
        // If yes, append the extra ingredients separated with comma
        for (int i = 0; i < ingredientsList.size(); i++) {
            if (i != 0) {
                ingredientsTextView.append(", ");
            }
            ingredientsTextView.append(ingredientsList.get(i));
        }

        // Set 'Place Of Origin' and 'Description'
        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());

        // If any data is not present then instead of showing a Blank Space,
        // the field will show "Not Found" message so that the user is sure that there is no data for that particular field
        if (placeOfOriginTextView.getText().toString().equals("")) {
            placeOfOriginTextView.append("Not Found");
        }
        if (alsoKnownAsList.size() == 0) {
            alsoKnownAsTextView.append("Not Found");
        }
        if (ingredientsList.size() == 0) {
            ingredientsTextView.append("Not Found");
        }
        if (descriptionTextView.getText().toString().equals("")) {
            descriptionTextView.append("Not Found");
        }
    }
}
