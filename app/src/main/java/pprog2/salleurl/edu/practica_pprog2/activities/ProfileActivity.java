package pprog2.salleurl.edu.practica_pprog2.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import pprog2.salleurl.edu.practica_pprog2.R;

/**
 * Created by Daniel on 23/5/17.
 */

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText name;
    private EditText surname;
    private RadioButton male;
    private EditText description;
    private MenuItem editProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage = (ImageView) findViewById(R.id.imgProfile);
        name = (EditText) findViewById(R.id.txtNameProfile);
        surname = (EditText) findViewById(R.id.txtSurnameProfile);
        male = (RadioButton) findViewById(R.id.rdbMaleProfile);
        description = (EditText) findViewById(R.id.txtDescriptionProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        editProfile = (MenuItem) findViewById(R.id.btnEdit);
        return true;
    }
}
