package pprog2.salleurl.edu.practica_pprog2.repositories.implementations;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;
import pprog2.salleurl.edu.practica_pprog2.repositories.LocationsRepo;
import pprog2.salleurl.edu.practica_pprog2.utils.HttpRequestHelper;


public class LocationsWebService implements LocationsRepo {

    private static final String OMDBAPI_URI_BASE = "http://testapi-pprog2.azurewebsites.net/api/locations.php?method=getLocations";
    private static final String TOTAL_RESULTS_TAG = "totalResults";
    private static final String SEARCH_TAG = "Search";

    private static final String NAME_TAG = "name";
    private static final String TYPE_TAG = "type";
    private static final String LOCATION_TAG = "location";
    private static final String LATITUDE_TAG = "lat";
    private static final String LONGITUDE_TAG = "lng";
    private static final String ADDRESS_TAG = "address";
    private static final String OPENING_TAG = "opening";
    private static final String CLOSING_TAG = "closing";
    private static final String REVIEW_TAG = "review";
    private static final String DESCRIPTION_TAG = "description";




    @Override
    public List<FoodLocal> search(String searchParameter) {
            List<FoodLocal> list = null;
            HttpRequestHelper requestHelper = HttpRequestHelper.getInstance();
            JSONArray search = requestHelper.doHttpRequest(OMDBAPI_URI_BASE + searchParameter, "GET");
            //Log.d(MoviesWebService.class.getName(), json.toString());
            try {
                //Log.d(MoviesWebService.class.getName(), OMDBAPI_URI_BASE + searchParameter);

                list = new ArrayList<>(search.length());
                for (int i = 0; i < search.length(); i++) {
                    FoodLocal loc = new FoodLocal();
                    loc.setName(search.getJSONObject(i).getString(NAME_TAG));
                    loc.setType(search.getJSONObject(i).getString(TYPE_TAG));
                    JSONObject object = search.getJSONObject(i).getJSONObject(LOCATION_TAG);
                    loc.setLatitude(object.getDouble(LATITUDE_TAG));
                    loc.setLongitude(object.getDouble(LONGITUDE_TAG));
                    loc.setAddress(search.getJSONObject(i).getString(ADDRESS_TAG));
                    loc.setOpeningHour(search.getJSONObject(i).getString(OPENING_TAG));
                    loc.setClosingHour(search.getJSONObject(i).getString(CLOSING_TAG));
                    loc.setRating(search.getJSONObject(i).getDouble(REVIEW_TAG));
                    loc.setDescription(search.getJSONObject(i).getString(DESCRIPTION_TAG));

                    list.add(loc);
                }

                //Log.d(MoviesWebService.class.getName(), search.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
    }

}