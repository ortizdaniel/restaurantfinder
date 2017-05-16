package edu.salleurl.pprog2.practica_pprog2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by David on 29/03/2017.
 */

public class DescriptionActivity extends AppCompatActivity {

    private FloatingActionButton favButton;
    private boolean favorited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_descripcion);

        favorited = false;

        favButton = (FloatingActionButton) findViewById(R.id.favButton);
    }

    public void onFavButtonClick(View view){
        favorited = !favorited;
        if (favorited){
            favButton.setImageResource(R.drawable.favorite);
        }else{
            favButton.setImageResource(R.drawable.favorite_empty);
        }
    }
}
