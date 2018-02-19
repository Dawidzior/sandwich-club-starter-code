package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    // Statics for cleaner JSON parsing.
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    private static final String CLASS_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String jsonString) {
        String mainName = "", placeOfOrigin = "", description = "", imageLink = "";
        ArrayList<String> alsoKnownAsList = new ArrayList<>(), ingredients = new ArrayList<>();

        try {
            JSONObject sandwich = new JSONObject(jsonString);

            if (sandwich.getJSONObject(NAME).has(MAIN_NAME)) {
                mainName = sandwich.getJSONObject(NAME).getString(MAIN_NAME);
            }

            if (sandwich.getJSONObject(NAME).has(ALSO_KNOWN_AS)) {
                JSONArray alsoKnownAsJsonList = sandwich.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
                for (int i = 0; i < alsoKnownAsJsonList.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJsonList.getString(i));
                }
            }

            if (sandwich.has(PLACE_OF_ORIGIN)) {
                placeOfOrigin = sandwich.getString(PLACE_OF_ORIGIN);
            }

            if (sandwich.has(DESCRIPTION)) {
                description = sandwich.getString(DESCRIPTION);
            }

            if (sandwich.has(IMAGE)) {
                imageLink = sandwich.getString(IMAGE);
            }

            if (sandwich.has(INGREDIENTS)) {
                JSONArray ingredientsJsonList = sandwich.getJSONArray(INGREDIENTS);
                for (int i = 0; i < ingredientsJsonList.length(); i++) {
                    ingredients.add(ingredientsJsonList.getString(i));
                }
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageLink, ingredients);

        } catch (JSONException e) {
            Log.e(CLASS_TAG, "Invalid JSONObject.", e);
        }
        return null;
    }
}
