package pprog2.salleurl.edu.practica_pprog2.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.adapters.RecentSearchesAdapter;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.model.RecentSearch;
import pprog2.salleurl.edu.practica_pprog2.repositories.LocationsRepo;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.LocationsWebService;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.RecentSearchesDB;
import pprog2.salleurl.edu.practica_pprog2.repositories.RecentSearchesRepo;
import pprog2.salleurl.edu.practica_pprog2.services.LocationService;


public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView recentSearchesListView;
    private SeekBar radiusSeekBar;
    private LocationsRepo locationsRepo;
    private TextView radiusTextView;
    private ArrayList<RecentSearch> recentSearches;
    private RecentSearchesAdapter recentSearchesAdapter;
    private RecentSearch lastRecentSearch;
    private boolean saveLastSearch;
    private RecentSearchesRepo recentSearchesRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        saveLastSearch = false;
        LocationService.getInstance(getApplicationContext());

        radiusTextView = (TextView) findViewById(R.id.search_radius_km_text_view);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        recentSearchesListView = (ListView) findViewById(R.id.search_recent_searches_list_view);
        radiusSeekBar = (SeekBar) findViewById(R.id.seekBar);
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radiusTextView.setText(String.valueOf(progress + 1) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        recentSearchesRepo = new RecentSearchesDB(this);
        recentSearches = recentSearchesRepo.getRecentSearches();
        recentSearchesAdapter = new RecentSearchesAdapter(this, recentSearches);
        recentSearchesListView.setAdapter(recentSearchesAdapter);
        recentSearchesListView.setOnItemClickListener(recentSearchesAdapter);


        locationsRepo = new LocationsWebService();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == searchEditText.getId()) {
                    onClickSearchByTextButton(v);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationService locationService = LocationService.getInstance(getApplicationContext());
        locationService.registerListeners(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_favorite_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fav_action_button:
                Intent favoritesIntent = new Intent();
                //TODO: añadir clase de favoritos
                startActivity(favoritesIntent);
                break;
            case R.id.menu_profile_action_button:
                Intent profileIntent = new Intent();
                //TODO: añadir clase de perfil
                startActivity(profileIntent);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Funcio per treure el cursor i el focus del EditText quan es clica a fora
     * Source: http://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
     * Author: http://stackoverflow.com/users/1591623/zman
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void onClickSearchGeolocationButton(View v) {
        Location location = LocationService.getInstance(this).getLocation();
        int radius = radiusSeekBar.getProgress() + 10000000;
        String searchParameters = "&lat=" + location.getLatitude() + "&lon=" + location.getLongitude()
                                    + "&dist=" + radius;
        System.out.println(searchParameters);
        lastRecentSearch = new RecentSearch();
        lastRecentSearch.setIsText(false);
        lastRecentSearch.setLatitude(location.getLatitude());
        lastRecentSearch.setLongitude(location.getLongitude());
        lastRecentSearch.setRadius(radius);
        saveLastSearch = true;
        new AsyncRequest(this).execute(searchParameters);
    }

    public void onClickSearchClearButton(View v) {
        searchEditText.setText("");
    }

    public void onClickSearchByTextButton(View v) {
        if (!searchEditText.getText().toString().isEmpty()) {
            System.out.println("PRESSING");
            String searchParameters = "&s=" + searchEditText.getText().toString();
            lastRecentSearch = new RecentSearch();
            lastRecentSearch.setIsText(true);
            lastRecentSearch.setSearchText(searchEditText.getText().toString());
            saveLastSearch = true;
            new AsyncRequest(this).execute(searchParameters);
        }
    }

    public void makeSearchAsyncRequest(String searchParameters) {
        new AsyncRequest(this).execute(searchParameters);
    }

    private class AsyncRequest extends AsyncTask<String, Void, List<FoodLocal>> {

        private Context context;
        private ProgressDialog progressDialog;

        protected AsyncRequest(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
        }

        @Override
        protected List<FoodLocal> doInBackground(String... params) {
            return locationsRepo.search(params[0]);
        }

        @Override
        protected void onPostExecute(List<FoodLocal> aList) {
            super.onPostExecute(aList);
            if (aList.isEmpty()) {
                Toast.makeText(context, getString(R.string.search_no_locations_found), Toast.LENGTH_SHORT)
                        .show();
            } else {
                if (saveLastSearch) {
                    recentSearchesRepo.insertRecentSearch(lastRecentSearch);
                    recentSearches.add(0, lastRecentSearch);
                    recentSearchesAdapter.notifyDataSetChanged();
                }
                saveLastSearch = false;
                ArrayList<FoodLocal> locationsList = new ArrayList<>(aList);

                Intent intent = new Intent(context, ResultsActivity.class);
                Bundle bundle = new Bundle();
                System.out.println("size" + aList.size());
                System.out.println("size" + locationsList.size());
                bundle.putParcelableArrayList("LOCATIONS", locationsList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LocationService.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationService.getInstance(this).registerListeners(this);
                }
                break;
        }
    }
}


