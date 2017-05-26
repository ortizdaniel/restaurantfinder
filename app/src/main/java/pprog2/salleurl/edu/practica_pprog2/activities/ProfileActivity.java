package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.UserServiceDB;

/**
 * Created by Daniel on 23/5/17.
 */

public class ProfileActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;

    private ImageView profileImage;
    private Button takePicture;
    private Button save;
    private EditText name;
    private EditText surname;
    private RadioButton male;
    private RadioButton female;
    private EditText description;
    private boolean editable;
    private User user;
    private Bitmap image;

    private UserServiceDB repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage = (ImageView) findViewById(R.id.imgProfile);
        takePicture = (Button) findViewById(R.id.btnTakePictureProfile);
        name = (EditText) findViewById(R.id.txtNameProfile);
        surname = (EditText) findViewById(R.id.txtSurnameProfile);
        male = (RadioButton) findViewById(R.id.rdbMaleProfile);
        female = (RadioButton) findViewById(R.id.rdbFemaleProfile);
        description = (EditText) findViewById(R.id.txtDescriptionProfile);
        save = (Button) findViewById(R.id.btnSave);
        editable = false;
        takePicture.setEnabled(false);
        name.setEnabled(false);
        surname.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        description.setEnabled(false);
        save.setEnabled(false);
        save.setVisibility(View.GONE);

        user = MainActivity.getActualUser();

        name.setText(user.getNombre());
        surname.setText(user.getApellidos());
        male.setChecked(user.getSexo() == User.MALE);
        female.setChecked(user.getSexo() == User.FEMALE);
        description.setText(user.getDescription());
        profileImage.setImageBitmap(user.getProfileImage());
        image = user.getProfileImage();

        repo = new UserServiceDB(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public void onEditPictureClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void onFavoritesClick(MenuItem item) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void onSaveButtonClick(View view) {
        user.setNombre(name.getText().toString());
        user.setApellidos(surname.getText().toString());
        user.setSexo(male.isChecked() ? User.MALE : User.FEMALE);
        user.setDescription(description.getText().toString());
        user.setProfileImage(image);

        repo.updateUser(user);
    }

    public void editButtonClick(MenuItem view) {
        editable = !editable;
        save.setEnabled(editable);
        save.setVisibility(editable ? View.VISIBLE : View.GONE);
        takePicture.setEnabled(editable);
        name.setEnabled(editable);
        surname.setEnabled(editable);
        male.setEnabled(editable);
        female.setEnabled(editable);
        description.setEnabled(editable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap image = (Bitmap) bundle.get("data");
                    if (image != null && profileImage != null) {

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

                        ExifInterface ei = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            try {
                                ei = new ExifInterface(bs);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }

                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    image = rotateImage(image, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    image = rotateImage(image, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    image = rotateImage(image, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:

                                default:
                                    break;
                            }
                        } else {
                            if (image.getHeight() < image.getWidth()) {
                                image = rotateImage(image, -90);
                            }
                        }

                        profileImage.setImageBitmap(image);
                        this.image = image;
                    }
                }
                break;
        }
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
