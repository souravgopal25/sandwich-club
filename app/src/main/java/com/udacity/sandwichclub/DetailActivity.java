package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG=DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView origin,description,alsoKnownAs,ingredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        origin=findViewById(R.id.placeoforigin);
        description=findViewById(R.id.description);
        ingredient=findViewById(R.id.ingredientlist);
        alsoKnownAs=findViewById(R.id.alsoknownas);

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
        if(sandwich==null){
            Log.e(TAG,"SANDWICH IS NULL");
        }




        String strGetAlsoKnownAs="";
        String strIngredient="";
        if(sandwich.getAlsoKnownAs()!=null){
            for(int j=0;j<sandwich.getAlsoKnownAs().size();j++){
                strGetAlsoKnownAs=strGetAlsoKnownAs+"\n"+sandwich.getAlsoKnownAs().get(j);

            }
            alsoKnownAs.setText(strGetAlsoKnownAs);


        }else{
            alsoKnownAs.setText("No information");
        }
        if(sandwich.getIngredients()!=null){
            //Toast.makeText(this, "INGREDIENT", Toast.LENGTH_SHORT).show();
            for(int i=0;i<sandwich.getIngredients().size();i++){
                strIngredient=strIngredient+"\n"+sandwich.getIngredients().get(i);
            }
            Toast.makeText(this, "SIZE OF LIST"+sandwich.getIngredients().size(), Toast.LENGTH_SHORT).show();
            ingredient.setText(strIngredient);

        }else {
            ingredient.setText("No information");
        }
        origin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());

        Log.v(TAG,"SUCCESSFULLY LOADED");


    }
}
