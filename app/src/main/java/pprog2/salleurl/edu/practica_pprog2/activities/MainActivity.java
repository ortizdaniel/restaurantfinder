package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pprog2.salleurl.edu.practica_pprog2.R;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;

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

        }
    }
    public void onRegisterButtonClick(View view){
        //TODO esperando a k dani haga su clase
        /* Llamamos a la actividad de Registro */
        //Intent intent = new Intent(this, /*Register Activity.class*/);
        //startActivity(intent);
    }
}
