package pprog2.salleurl.edu.practica_pprog2.repositories.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.UsersRepo;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Created by David on 22/05/2017.
 */
public class UserServiceDB implements UsersRepo {

    /* Database String Constants */
    public static final String TABLE_NAME_USER = "User";
    public static final String NAME_USER = "nombre";
    public static final String PASSWORD_USER = "password";
    public static final String SURNAME_USER = "apellidos";
    public static final String IMAGE_USER = "profileImage";
    public static final String EMAIL_USER = "email";
    public static final String SEXO_USER = "sexo";
    public static final String DESCRIPTION_USER = "description";

    private String UPDATE_QUERY = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, " +
            "%s = ?, %s = ? WHERE %s = ?", TABLE_NAME_USER, NAME_USER, SURNAME_USER,
            IMAGE_USER, SEXO_USER, DESCRIPTION_USER, EMAIL_USER);

    private Context ctx;
    public UserServiceDB(Context ctx){this.ctx = ctx;}

    @Override
    public void addUser(User u) {
        // Recuperamos una instancia del DatabaseHelper para poder acceder a la base de datos.
        DatabaseHelper helper = DatabaseHelper.getInstance(ctx);

        // Creamos los valores a añadir a la base de datos mediante un conjunto <Clave, Valor>.
        ContentValues values = new ContentValues();
        values.put(NAME_USER, u.getNombre());
        values.put(SURNAME_USER, u.getApellidos());
        values.put(IMAGE_USER, u.getProfileImageName());
        values.put(EMAIL_USER,u.getEmail());
        values.put(DESCRIPTION_USER,u.getDescription());
        values.put(SEXO_USER,String.valueOf(u.getSexo()));
        // Obtenemos la base de datos en modo escritura e inserimos los valores en la Tabla
        helper.getWritableDatabase().insert(TABLE_NAME_USER, null, values);
    }


    @Override
    public User getUser(String email, String password) {
        User u = null;
        DatabaseHelper helper = DatabaseHelper.getInstance(ctx);

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = EMAIL_USER + "=? AND " + PASSWORD_USER + "=?";

        String[] whereArgs = {email,password};

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
                    byte[] image_path = cursor.getBlob(cursor.getColumnIndex(IMAGE_USER));
                    String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_USER));
                    String email_get = cursor.getString(cursor.getColumnIndex(EMAIL_USER));
                    char sexo = cursor.getString(cursor.getColumnIndex(SEXO_USER)).charAt(0);
                    u = new User(nombre, apellidos, image_path,description,email_get,sexo, ctx);
                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return u;
    }

    @Override
    public void updateUser(User u) {
        SQLiteStatement stmt = DatabaseHelper.getInstance(ctx).getWritableDatabase().compileStatement(UPDATE_QUERY);
        stmt.bindString(1, u.getNombre());
        stmt.bindString(2, u.getApellidos());
        byte[] imagenBytes = u.getImagenBytes();
        if (imagenBytes == null) {
            stmt.bindNull(3);
        } else {
            stmt.bindBlob(3, imagenBytes);
        }
        stmt.bindString(4, String.valueOf(u.getSexo()));
        stmt.bindString(5, u.getDescription());
        stmt.bindString(6, u.getEmail());
        if (stmt.executeUpdateDelete() > 0) {
            Toast.makeText(ctx, ctx.getString(R.string.information_updated_success),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ctx, ctx.getString(R.string.information_update_error),
                    Toast.LENGTH_LONG).show();
        }
    }
}
