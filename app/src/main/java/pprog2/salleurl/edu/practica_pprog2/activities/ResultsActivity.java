package pprog2.salleurl.edu.practica_pprog2.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.adapters.TabAdapter;
import pprog2.salleurl.edu.practica_pprog2.fragments.ResultsListViewFragment;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

public class ResultsActivity extends AppCompatActivity {

    private Spinner actionBarSpinner;

    private ResultsListViewFragment allFragment;
    private ResultsListViewFragment onlyOpenFragment;
    private ArrayList<FoodLocal> foodLocals;
    private List<String> restaurantTypes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        restaurantTypes = new ArrayList<>();
        restaurantTypes.add(getString(R.string.all_types));

        createTabs();
        createToolbar();

        if (getIntent() != null && getIntent().getExtras() != null) {
            foodLocals = getIntent().getExtras().getParcelableArrayList("LOCATIONS");

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
            allFragment.newLocations(foodLocals);
            onlyOpenFragment.newLocations(foodLocals);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_results_menu, menu);

        MenuItem item = menu.findItem(R.id.activity_results_menu_spinner);
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
            case R.id.menu_favorites_button:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_profile_action_button:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            default:
                break;
        }
        return true;
    }
}
