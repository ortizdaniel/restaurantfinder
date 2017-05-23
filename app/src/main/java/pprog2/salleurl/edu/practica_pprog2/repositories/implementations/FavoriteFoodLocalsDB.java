package pprog2.salleurl.edu.practica_pprog2.repositories.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;

import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.model.RecentSearch;
import pprog2.salleurl.edu.practica_pprog2.repositories.FavoriteFoodLocalsRepo;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Created by Mundirisa on 22/05/2017.
 */

public class FavoriteFoodLocalsDB implements FavoriteFoodLocalsRepo {

    private static final String TABLE_NAME = "FavoritePlace";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_OPENING_HOUR = "openingHour";
    private static final String COLUMN_CLOSING_HOUR = "closingHour";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_DESCRIPTION_HOUR = "description";

    private Context context;

    public FavoriteFoodLocalsDB(Context context) {
        this.context = context;
    }


    @Override
    public List<FoodLocal> getFavoriteFoodLocals(String userEmail) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        String whereClause = COLUMN_USER_EMAIL + " = ?";
        String orderByClause = COLUMN_NAME + " ASC";
        String[] whereArgs = {userEmail};

        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, whereClause, whereArgs, null, null, orderByClause, null);
        ArrayList<FoodLocal> favoriteLocals = new ArrayList<>();

        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    FoodLocal foodLocal = new FoodLocal();
                    foodLocal.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    foodLocal.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                    foodLocal.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
                    foodLocal.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    foodLocal.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                    foodLocal.setOpeningHour(cursor.getString(cursor.getColumnIndex(COLUMN_OPENING_HOUR)));
                    foodLocal.setClosingHour(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSING_HOUR)));
                    foodLocal.setRating(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING)));
                    foodLocal.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_HOUR)));

                    favoriteLocals.add(foodLocal);
                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return favoriteLocals;
    }

    @Override
    public void insertFavoriteFoodLocal(String userEmail, FoodLocal foodLocal) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL,userEmail);
        values.put(COLUMN_NAME, foodLocal.getName());
        values.put(COLUMN_TYPE, foodLocal.getType());
        values.put(COLUMN_LATITUDE, foodLocal.getLatitude());
        values.put(COLUMN_LONGITUDE, foodLocal.getLongitude());
        values.put(COLUMN_ADDRESS, foodLocal.getAddress());
        values.put(COLUMN_OPENING_HOUR, foodLocal.getOpeningHour());
        values.put(COLUMN_CLOSING_HOUR, foodLocal.getClosingHour());
        values.put(COLUMN_RATING, foodLocal.getRating());
        values.put(COLUMN_DESCRIPTION_HOUR, foodLocal.getDescription());

        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    @Override
    public void deleteFavoriteFoodLocal(String userEmail, FoodLocal foodLocal) {
        // Recuperamos una instancia del DatabaseHelper para poder acceder a la base de datos.
        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = COLUMN_USER_EMAIL + "=? and " + COLUMN_ADDRESS + "=? and " + COLUMN_NAME +"=?";
        // Preparamos los argumentos a sustituir por los '?' de la cláusula.
        String[] whereArgs = {userEmail, foodLocal.getAddress(),foodLocal.getName()};

        helper.getWritableDatabase().delete(TABLE_NAME, whereClause, whereArgs);
        // El delete anterior equivale a la query SQL:
        // delete from TABLE_NAME where COLUMN_NAME=p.getName() and COLUMN_SURNAME=p.getSurname();
    }

    @Override
    public boolean isFavorite(String userEmail, FoodLocal foodLocal) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = COLUMN_USER_EMAIL + "=? and " + COLUMN_ADDRESS + "=? and " + COLUMN_NAME +"=?";
        // Preparamos los argumentos a sustituir por los '?' de la cláusula.
        String[] whereArgs = {userEmail, foodLocal.getAddress(),foodLocal.getName()};

        SQLiteDatabase db = helper.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME, whereClause, whereArgs);

        return count > 0;
    }
}
