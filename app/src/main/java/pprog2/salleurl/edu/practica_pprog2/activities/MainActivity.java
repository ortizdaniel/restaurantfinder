package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.UsersRepo;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.UserServiceDB;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;
    private UsersRepo usersRepo;
    private static User actualUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersRepo = new UserServiceDB(getApplicationContext());

        username = (TextInputEditText) findViewById(R.id.login);
        password = (TextInputEditText) findViewById(R.id.password);
    }
    public void onLoginButtonClick(View view){
        if(username.getText().toString().equals("")){
            username.setError(getString(R.string.no_login));
        }else if(password.getText().toString().equals("")){
            password.setError(getString(R.string.no_password));
        }else{
            User user = usersRepo.getUser(username.getText().toString(),password.getText().toString());

            // v Esto de debajo es para el que sergi pueda probar sin loggear v
            /*if(user == null) {
                user = new User();
            }*/
            //User user = new User();
            //hasta aqui
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
}
