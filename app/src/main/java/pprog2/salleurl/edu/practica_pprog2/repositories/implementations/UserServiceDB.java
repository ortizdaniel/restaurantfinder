package pprog2.salleurl.edu.practica_pprog2.repositories.implementations;

import android.content.Context;
import android.database.Cursor;

import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.UsersRepo;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Created by David on 22/05/2017.
 */

public class UserServiceDB implements UsersRepo {

    /* Database String Constants */
    private static final String TABLE_NAME_USER = "User";
    private static final String NAME_USER = "nombre";
    private static final String PASSWORD_USER = "password";
    private static final String SURNAME_USER = "apellidos";
    private static final String IMAGE_USER = "profileImage";
    private static final String EMAIL_USER = "email";
    private static final String SEXO_USER = "sexo";
    private static final String DESCRIPTION_USER = "description";

    private Context context;
    public UserServiceDB(Context context){this.context = context;}

    @Override
    public void addUser(String nombre,String  apellidos,String image_path,String description,String email,char sexo) {

    }

    @Override
    public User getUser(String username, String password) {
        User u = null;
        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = NAME_USER + "=? AND " + PASSWORD_USER + "=?";

        String[] whereArgs = {username,password};

        Cursor cursor = helper.getReadableDatabase().
                query(TABLE_NAME_USER, null, whereClause, whereArgs, null, null, null);

        // La query equivale en SQL a:
        // select * from TABLE_NAME where COLUMN_NAME=name and COLUMN_SURNAME=surname;
        // Los 2 nulls del final equivalen al GROUPBY, HAVING y el ultimo valor
        // equivale a ORDERBY.

        // Comprobamos que se nos haya devuelto un cursor válido.
        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(cursor.getColumnIndex(NAME_USER));
                    String apellidos = cursor.getString(cursor.getColumnIndex(SURNAME_USER));
                    String image_path = cursor.getString(cursor.getColumnIndex(IMAGE_USER));
                    String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_USER));
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL_USER));
                    char sexo = cursor.getString(cursor.getColumnIndex(SEXO_USER)).charAt(0);
                    u = new User(nombre, apellidos, image_path,description,email,sexo);
                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return u;
    }

    @Override
    public void updateUser() {

    }
}
