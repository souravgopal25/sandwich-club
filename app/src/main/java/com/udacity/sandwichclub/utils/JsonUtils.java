package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    //TAG FOR LOG MESSAGE
    public static final String TAG=JsonUtils.class.getSimpleName();

    //ASSSOCIATED KEY OF JSON
    public static final String KEY_NAME="name";
    public static final String KEY_MAIN_VALUE="mainName";
    public static final String KEY_ALSO_KNOWN_AS="alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN="placeOfOrigin";
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_IMAGE="image";
    public static final String KEY_INGREDIENTS="ingredients";


    //RETURNS a Sandwich Object to populate the UI by parsing JSON
    public static Sandwich parseSandwichJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {   //EXTRACTING WHOLE JSON
                JSONObject baseJson =new JSONObject(json);

                //EXTRACTING NAME BRACH FROM JSON
                JSONObject name=baseJson.getJSONObject(KEY_NAME);

                String mainName=name.getString(KEY_MAIN_VALUE);
                JSONArray alsoKnownAsArray=name.getJSONArray(KEY_ALSO_KNOWN_AS);

                //create an empty array list to store
                List<String> alsoKnownAs=new ArrayList<>();
                //if json is not empty add elements to the list
                if(alsoKnownAsArray.length()!=0){
                    for (int i = 0; i <alsoKnownAsArray.length(); i++) {
                            alsoKnownAs.add(alsoKnownAsArray.getString(i).toString());

                    }
                }else{
                    alsoKnownAs=null;
                }
                String placeOfOrigin=baseJson.getString(KEY_PLACE_OF_ORIGIN);
                if (placeOfOrigin.isEmpty()) {
                    placeOfOrigin = null;
                }
                String description=baseJson.getString(KEY_DESCRIPTION);

                String imageUrl = baseJson.getString(KEY_IMAGE);

                JSONArray ingredientsArrays=baseJson.getJSONArray(KEY_INGREDIENTS);
                List<String> ingredients = new ArrayList<>();
                if (ingredientsArrays.length() != 0) {
                for (int i = 0; i <ingredientsArrays.length() ; i++) {
                    ingredients.add(ingredientsArrays.getString(i).toString());

                }

                 }else {
                     ingredients = null;
                }
                Log.v(TAG,"SANDWICH  OBJECT DETAILS");
                Log.v(TAG,"main NAme :"+mainName);

                return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,imageUrl,ingredients);





        } catch (JSONException e) {
            Log.e(TAG,"Problem Parsing JSON",e);
            return null;
        }
    }
}
