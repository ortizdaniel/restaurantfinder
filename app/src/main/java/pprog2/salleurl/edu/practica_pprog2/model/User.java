package pprog2.salleurl.edu.practica_pprog2.model;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * Created by David on 22/05/2017.
 */

public class User {
    private String nombre;
    private String apellidos;
    private Bitmap profileImage;
    private String email;
    private char sexo;
    private String description;

    public User(){}

    public User(String nombre,String  apellidos,String image_path,String description,String email,char sexo){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.description = description;
        this.email = email;
        this.sexo = sexo;
        //TODO add photo
        //Environment.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }
}
