package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.UsersRepo;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.UserServiceDB;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText userEmail;
    private TextInputEditText password;
    private UsersRepo usersRepo;
    private static User actualUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersRepo = new UserServiceDB(getApplicationContext());

        userEmail = (TextInputEditText) findViewById(R.id.login);
        password = (TextInputEditText) findViewById(R.id.password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }
    public void onLoginButtonClick(View view){
        login();
    }
    private void login(){
        if(userEmail.getText().toString().equals("")){
            userEmail.setError(getString(R.string.no_login));
        }else if(password.getText().toString().equals("")){
            password.setError(getString(R.string.no_password));
        }else{
            User user = usersRepo.getUser(userEmail.getText().toString(),password.getText().toString());

            if(user == null){
                /* Login Unable */
                Toast.makeText(this, getString(R.string.unable_login), Toast.LENGTH_SHORT)
                        .show();
            }else{
                actualUser = user;
                /* Llamamos a la Activity de Busqueda */
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
            }
        }
    }
    public void onRegisterButtonClick(View view){
        /* Llamamos a la actividad de Registro */
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public static User getActualUser() {
        return actualUser;
    }
    public static void setActualUser(User user){
        actualUser = user;
    }
}
