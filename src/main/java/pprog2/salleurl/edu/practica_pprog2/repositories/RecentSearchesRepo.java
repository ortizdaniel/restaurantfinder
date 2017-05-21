package pprog2.salleurl.edu.practica_pprog2.repositories;

import android.content.Context;

import java.util.ArrayList;

import pprog2.salleurl.edu.practica_pprog2.model.RecentSearch;

/**
 * Created by Mundirisa on 30/03/2017.
 */

public interface RecentSearchesRepo {
    ArrayList<RecentSearch> getRecentSearches();
    void insertRecentSearch(RecentSearch recentSearch);
}
