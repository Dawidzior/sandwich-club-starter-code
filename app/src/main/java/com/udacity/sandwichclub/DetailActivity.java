package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String COMMA_AND_SPACE = ", ";
    public static final String DATA_UNAVAILABLE_TEXT = "Data unavailable";

    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAs, placeOfOrigin, ingredients, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAs = findViewById(R.id.also_known_tv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        alsoKnownAs.setText(TextUtils.join(COMMA_AND_SPACE, sandwich.getAlsoKnownAs()));
        if (alsoKnownAs.getText().toString().isEmpty()) alsoKnownAs.setText(DATA_UNAVAILABLE_TEXT);

        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        if (placeOfOrigin.getText().toString().isEmpty()) placeOfOrigin.setText(DATA_UNAVAILABLE_TEXT);


        ingredients.setText(TextUtils.join(COMMA_AND_SPACE, sandwich.getIngredients()));
        if (ingredients.getText().toString().isEmpty()) ingredients.setText(DATA_UNAVAILABLE_TEXT);


        description.setText(sandwich.getDescription());
        if (description.getText().toString().isEmpty()) description.setText(DATA_UNAVAILABLE_TEXT);
    }
}
