package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.UsersRepo;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;
    private UsersRepo usersRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if(user == null) {
                user = new User();
            }
            //hasta aqui
            if(user == null){
                /* Login Unable */
                Toast.makeText(this, getString(R.string.unable_login), Toast.LENGTH_SHORT)
                        .show();
            }else{
                /* Llamamos a la Activity de Busqueda */
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
            }
        }
    }
    public void onRegisterButtonClick(View view){
        //TODO esperando a k dani haga su clase
        /* Llamamos a la actividad de Registro */
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
