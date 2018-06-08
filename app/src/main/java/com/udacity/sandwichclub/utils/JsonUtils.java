package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject data = new JSONObject(json);
            JSONObject name = data.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);

            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = data.getString(PLACE_OF_ORIGIN);
            String description = data.getString(DESCRIPTION);
            String image = data.getString(IMAGE);
            JSONArray ingredients = data.getJSONArray(INGREDIENTS);

            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
