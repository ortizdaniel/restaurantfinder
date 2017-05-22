package pprog2.salleurl.edu.practica_pprog2.repositories.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.RecentSearch;
import pprog2.salleurl.edu.practica_pprog2.repositories.RecentSearchesRepo;
import pprog2.salleurl.edu.practica_pprog2.utils.DatabaseHelper;

/**
 * Created by Mundirisa on 18/05/2017.
 */

public class RecentSearchesDB implements RecentSearchesRepo {
    // Contantes con los nombres de la tabla y de las columnas de la tabla.
    private static final String TABLE_NAME = "RecentSearch";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IS_TEXT = "isText";
    private static final String COLUMN_TEXT_SEARCH = "textSearch";
    private static final String COLUMN_LONGITUDE = "longitud";
    private static final String COLUMN_LATITUDE = "latitud";
    private static final String COLUMN_RADIUS = "radio";

    private Context context;

    public RecentSearchesDB(Context context) {
        this.context = context;
    }


    @Override
    public ArrayList<RecentSearch> getRecentSearches() {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        String[] selectColumns = null;
        String whereClause = null;
        String orderByClause = COLUMN_ID + " DESC";
        String limitClause = "50";

        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, selectColumns, null, null, null, null, orderByClause, limitClause);
        ArrayList<RecentSearch> list = new ArrayList<>();

        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    RecentSearch recentSearch = new RecentSearch();
                    if (cursor.getInt(cursor.getColumnIndex(COLUMN_IS_TEXT)) == 1) {
                        recentSearch.setIsText(true);
                        recentSearch.setSearchText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT_SEARCH)));
                    } else {
                        recentSearch.setIsText(false);
                        recentSearch.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
                        recentSearch.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                        recentSearch.setRadius(cursor.getInt(cursor.getColumnIndex(COLUMN_RADIUS)));
                    }
                    list.add(recentSearch);
                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return list;
    }

    @Override
    public void insertRecentSearch(RecentSearch recentSearch) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        ContentValues values = new ContentValues();
        System.out.println("addingggreg");
        if (recentSearch.isText()) {
            values.put(COLUMN_IS_TEXT, 1);
            values.put(COLUMN_TEXT_SEARCH, recentSearch.getSearchText());
        } else {
            values.put(COLUMN_IS_TEXT, 0);
            values.put(COLUMN_LATITUDE, recentSearch.getLatitude());
            values.put(COLUMN_LONGITUDE, recentSearch.getLatitude());
            values.put(COLUMN_RADIUS, recentSearch.getLatitude());
        }
        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }
}
