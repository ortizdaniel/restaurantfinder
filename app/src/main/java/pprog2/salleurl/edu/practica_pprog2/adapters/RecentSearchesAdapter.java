package pprog2.salleurl.edu.practica_pprog2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.activities.SearchActivity;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.model.RecentSearch;


/**
 * Created by Mundirisa on 30/03/2017.
 */

public class RecentSearchesAdapter extends ArrayAdapter<RecentSearch> implements AdapterView.OnItemClickListener {
    private List<RecentSearch> recentSearches;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        LayoutInflater itemInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            itemView = itemInflater.inflate(R.layout.search_recent_searches_list_view_item, parent, false);
        }
        else {
            itemView = convertView;
        }
        String textToShow;
        RecentSearch rs = recentSearches.get(position);
        if (rs.isText()) {
            textToShow = rs.getSearchText();
        } else {
            textToShow = "Lat = " + rs.getLatitude() + " Lon = " + rs.getLongitude() + " Radius = " + rs.getRadius() + " km";
        }

        TextView text = (TextView) itemView.findViewById(R.id.recent_searches_list_view_item_text_view);
        text.setText(textToShow);
        return itemView;
    }

    public RecentSearchesAdapter(@NonNull Context context, List<RecentSearch> recentSearches){
        super(context, R.layout.search_recent_searches_list_view_item, recentSearches);
        this.recentSearches = recentSearches;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchActivity activity = (SearchActivity) getContext();
        RecentSearch rs = recentSearches.get(position);
        String searchParameters;
        if (rs.isText()) {
            searchParameters = "&s=" + rs.getSearchText();
        } else {
            searchParameters = "&lat=" + rs.getLatitude() + "&lon=" + rs.getLongitude()
                    + "&dist=" + rs.getRadius();
        }
        activity.makeSearchAsyncRequest(searchParameters);
    }
}
