package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Clase registro
 *
 * @author Daniel
 * @version 1.0.0
 */
public class RegisterActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private static final String QUERY = "INSERT INTO User(nombre, apellidos, profileImage," +
            "email, sexo, password, description) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private ImageView profileImage;
    private EditText txtName;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private RadioButton isMale;
    private EditText txtDescription;
    private CheckBox accept;

    private boolean pictureTaken;
    private Bitmap imageTaken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profileImage = (ImageView) findViewById(R.id.imageView);
        txtName = (EditText) findViewById(R.id.txtName);
        txtSurname = (EditText) findViewById(R.id.txtSurname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        isMale = (RadioButton) findViewById(R.id.rdbMale);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        accept = (CheckBox) findViewById(R.id.chbAcceptTerms);
        pictureTaken = false;
    }

    public void onTakePictureClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void onReallyRegisterClick(View view) {
        if (accept.isChecked() && pictureTaken) {
            String name = txtName.getText().toString();
            String surname = txtSurname.getText().toString();
            //IMAGE TAKEN AQUI
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();
            char sex = isMale.isSelected() ? User.MALE : User.FEMALE;
            String description = txtDescription.getText().toString();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    description.isEmpty()) {
                //TODO CORREGIR TOAST
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals(confirmPassword)) {
                SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase();
                SQLiteStatement stmt = db.compileStatement(QUERY);
                stmt.bindString(1, name);
                stmt.bindString(2, surname);
                stmt.bindNull(3); //TODO CORREGIR IMAGEN ESTA A NULO AHORA
                stmt.bindString(4, email);
                stmt.bindString(5, String.valueOf(sex));
                stmt.bindString(6, password);
                stmt.bindString(7, description);
                if (stmt.executeInsert() != -1) {
                    Toast.makeText(this, "REGISTRADO", Toast.LENGTH_SHORT).show(); //TODO CORREGIR
                } else {
                    //TODO CORREGIR TOAST
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            } else {
                //TODO CORREGIR TOAST
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        } else {
            //TODO CORREGIR TOAST
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap image = (Bitmap) bundle.get("data");
                    if (profileImage != null) {
                        profileImage.setImageBitmap(image);
                        pictureTaken = true;
                        imageTaken = image;
                    }
                }
                break;
        }
    }
}
