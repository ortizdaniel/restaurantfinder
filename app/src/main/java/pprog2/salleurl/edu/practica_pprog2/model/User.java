package pprog2.salleurl.edu.practica_pprog2.model;

import android.graphics.Bitmap;

/**
 * Created by David on 22/05/2017.
 */

public class User {

    public static char MALE = 'M';
    public static char FEMALE = 'F';

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String  getProfileImageName(){
        return null;
    }
}
