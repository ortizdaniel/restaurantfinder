package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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

import java.io.ByteArrayOutputStream;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.UserServiceDB;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Clase registro
 *
 * @author Daniel
 * @version 1.0.0
 */
public class RegisterActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private static final String QUERY = String.format("INSERT INTO %s(%s, %s, %s, " +
            "%s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
            UserServiceDB.TABLE_NAME_USER, UserServiceDB.NAME_USER, UserServiceDB.SURNAME_USER,
            UserServiceDB.IMAGE_USER, UserServiceDB.EMAIL_USER, UserServiceDB.SEXO_USER,
            UserServiceDB.PASSWORD_USER, UserServiceDB.DESCRIPTION_USER);

    private ImageView profileImage;
    private EditText txtName;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private RadioButton isMale;
    private EditText txtDescription;
    private CheckBox accept;

    private byte[] imageTaken;

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
        imageTaken = null;
    }

    public void onTakePictureClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void onReallyRegisterClick(View view) {
        if (accept.isChecked()) {
            String name = txtName.getText().toString();
            String surname = txtSurname.getText().toString();

            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();
            char sex = isMale.isChecked() ? User.MALE : User.FEMALE;
            String description = txtDescription.getText().toString();

            /*if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    description.isEmpty()) {
                Toast.makeText(this, str(R.string.fields_empty), Toast.LENGTH_LONG).show();
                return;
            }*/

            if (email.isEmpty()) {
                Toast.makeText(this, str(R.string.email_empty), Toast.LENGTH_LONG).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(this, str(R.string.password_empty), Toast.LENGTH_LONG).show();
                return;
            }

            if (password.equals(confirmPassword)) {
                SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase();
                SQLiteStatement stmt = db.compileStatement(QUERY);
                stmt.bindString(1, name);
                stmt.bindString(2, surname);
                if (imageTaken == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindBlob(3, imageTaken);
                }
                stmt.bindString(4, email);
                stmt.bindString(5, String.valueOf(sex));
                stmt.bindString(6, password);
                stmt.bindString(7, description);
                try {
                    if (stmt.executeInsert() != -1) {
                        //Toast.makeText(this, str(R.string.registered), Toast.LENGTH_LONG).show();
                        MainActivity.setActualUser(new User(name,surname,imageTaken,description,email,sex,this));
                        Intent intent = new Intent(this, SearchActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, str(R.string.error_repeat_email), Toast.LENGTH_LONG).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(this, str(R.string.error_repeat_email), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, str(R.string.password_mismatch), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, str(R.string.terms_not_accepted), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap image = (Bitmap) bundle.get("data");
                    if (image != null && profileImage != null) {
                        profileImage.setImageBitmap(image);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imageTaken = stream.toByteArray();
                    }
                }
                break;
        }
    }

    private String str(int id) {
        return getString(id);
    }
}
