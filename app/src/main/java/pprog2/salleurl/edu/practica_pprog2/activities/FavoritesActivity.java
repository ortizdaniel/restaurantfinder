package pprog2.salleurl.edu.practica_pprog2.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.adapters.TabAdapter;
import pprog2.salleurl.edu.practica_pprog2.fragments.ResultsListViewFragment;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.model.User;
import pprog2.salleurl.edu.practica_pprog2.repositories.FavoriteFoodLocalsRepo;
import pprog2.salleurl.edu.practica_pprog2.repositories.implementations.FavoriteFoodLocalsDB;

public class FavoritesActivity extends AppCompatActivity {
    private ResultsListViewFragment allFragment;
    private ResultsListViewFragment onlyOpenFragment;
    private ArrayList<FoodLocal> foodLocals;
    private Spinner actionBarSpinner;
    private List<String> restaurantTypes;
    private ArrayAdapter<String> adapter;
    private FavoriteFoodLocalsRepo favoritesRepo;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        restaurantTypes = new ArrayList<>();
        restaurantTypes.add(getString(R.string.all_types));
        favoritesRepo = new FavoriteFoodLocalsDB(this);
        createTabs();
        createToolbar();

    }

    @Override
    protected void onStart() {
        super.onStart();
        User actualUser = MainActivity.getActualUser();

        if (actualUser != null) {
            new AsyncRequest(this).execute(MainActivity.getActualUser().getEmail());
        } else {
            finish();
        }
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void createTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        allFragment = new ResultsListViewFragment();
        onlyOpenFragment = new ResultsListViewFragment();

        entries.add(new TabAdapter.TabEntry(allFragment, getString(R.string.all_types)));
        entries.add(new TabAdapter.TabEntry(onlyOpenFragment, getString(R.string.only_open)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
            return favoritesRepo.getFavoriteFoodLocals(params[0]);
        }

        @Override
        protected void onPostExecute(List<FoodLocal> aList) {
            super.onPostExecute(aList);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (aList.isEmpty()) {
                Toast.makeText(context, getString(R.string.search_no_locations_found), Toast.LENGTH_SHORT)
                        .show();
            } else {
                foodLocals = new ArrayList<>(aList);
                allFragment.newLocations(foodLocals);
                onlyOpenFragment.newLocations(foodLocals);
                for (FoodLocal l : foodLocals) {
                    boolean trobat = false;
                    for (String s : restaurantTypes) {
                        if (l.getType().equals(s)) {
                            trobat = true;
                            break;
                        }
                    }
                    if (!trobat) {
                        restaurantTypes.add(l.getType());
                    }
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favorites_menu, menu);

        MenuItem item = menu.findItem(R.id.activity_favorites_menu_spinner);
        actionBarSpinner = (Spinner) MenuItemCompat.getActionView(item);


        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, restaurantTypes);
        actionBarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);
                allFragment.filterList(type, false);
                onlyOpenFragment.filterList(type, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        actionBarSpinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_favorites_menu_profile_button:
                break;
            default:
                break;
        }
        return true;
    }
}
