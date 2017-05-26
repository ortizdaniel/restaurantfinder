package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.Comment;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.repositories.FavoriteFoodLocalsRepo;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.FavoriteFoodLocalsDB;

/**
 * Created by David on 29/03/2017.krtr
 */
public class DescriptionActivity extends AppCompatActivity {

    private FloatingActionButton favButton;
    private boolean favorited;
    private ImageView localImageView;
    private TextView localNameTextView;
    private RatingBar localRatingBar;
    private TextView localDescriptionTextView;
    private FoodLocal foodLocal;
    private FavoriteFoodLocalsRepo favoriteFoodLocalsRepo;
    private LinearLayout llComments;
    private TextInputEditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_descripcion);
        favorited = false;
        favButton = (FloatingActionButton) findViewById(R.id.favButton);
        comment = (TextInputEditText) findViewById(R.id.add_comment);
        llComments = (LinearLayout) findViewById(R.id.listview_comments);
        foodLocal = null;
        favoriteFoodLocalsRepo = new FavoriteFoodLocalsDB(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            foodLocal = bundle.getParcelable("FOOD_LOCAL");
        } else {
            finish();
        }
        if (foodLocal != null) {
            localImageView = (ImageView) findViewById(R.id.image_place);
            localNameTextView = (TextView) findViewById(R.id.place_name);
            localDescriptionTextView = (TextView) findViewById(R.id.placeDescription);
            localRatingBar = (RatingBar) findViewById(R.id.rating_stars);

            localImageView.setImageResource(FoodLocal.getImageResourceId(foodLocal.getType()));
            localNameTextView.setText(foodLocal.getName());
            localDescriptionTextView.setText(foodLocal.getDescription());
            localRatingBar.setRating((float)foodLocal.getRating());
            if(favoriteFoodLocalsRepo.isFavorite(MainActivity.getActualUser().getEmail(),foodLocal)){
                favButton.setImageResource(R.drawable.favorite);
            }
        }

        comment.setOnEditorActionListener(new TextInputEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    sendComment();
                    return true;
                }
                return false;
            }
        });
        List<Comment> comments = favoriteFoodLocalsRepo.getComments(foodLocal.getName());
        for(Comment c : comments){
            TextView tvComments = new TextView(this);
            tvComments.setText("@" + c.getUser() + ":  " + c.getCommment());
            llComments.addView(tvComments);
        }
        // Creem l'adapter.
        //adapter = new CommentsAdapter(this,comments);

        // El vinculem a la ListView.
        //lvComments.setAdapter(adapter);
    }

    public void onSendButtonClick(View view){
        sendComment();
    }

    private void sendComment(){
        if(comment.getText().toString().equals("")){
            comment.setError(getString(R.string.unable_comment));
        }else{
            favoriteFoodLocalsRepo.addComment(MainActivity.getActualUser().getEmail(),
                    comment.getText().toString(),foodLocal.getName());
            TextView tvComments = new TextView(this);
            tvComments.setText("@"+MainActivity.getActualUser().getNombre() + ":  " + comment.getText().toString());
            llComments.addView(tvComments);
        }
    }

    public void onFavButtonClick(View view){
        favorited = !favorited;
        if (favorited){
            favButton.setImageResource(R.drawable.favorite);
            /* Add the place to favorites list */
            favoriteFoodLocalsRepo.insertFavoriteFoodLocal(MainActivity.getActualUser().getEmail(),
                    foodLocal);
        }else{
            favButton.setImageResource(R.drawable.favorite_empty);
            /* Delete the favorite place from DB */
            favoriteFoodLocalsRepo.deleteFavoriteFoodLocal(MainActivity.getActualUser().getEmail(),
                    foodLocal);
        }
    }

    public void onMapButtonClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(MapsActivity.RESTRAURANT_EXTRA, foodLocal);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_favorite_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            /* Comprovem quin boto de la action bar s'ha permut */
            case R.id.menu_fav_action_button:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_profile_action_button:
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            default:

        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
