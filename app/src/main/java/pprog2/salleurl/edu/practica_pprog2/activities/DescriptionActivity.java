package pprog2.salleurl.edu.practica_pprog2.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;

/**
 * Created by David on 29/03/2017.krtr
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_description, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            /* Comprovem quin boto de la action bar s'ha permut */
            case R.id.menu_fav_action_button:
                /* fav intent */
                break;
            case R.id.menu_profile_action_button:
                /* profile Intent */
                break;
            default:

        }
        return true;
    }
}
