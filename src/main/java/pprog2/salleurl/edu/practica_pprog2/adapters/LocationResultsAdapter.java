package pprog2.salleurl.edu.practica_pprog2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

/**
 * Created by Mundirisa on 16/05/2017.
 */
public class LocationResultsAdapter extends ArrayAdapter<FoodLocal> {
    private ArrayList<FoodLocal> foodlocals;
    private Context context;

    public LocationResultsAdapter(@NonNull Context context, ArrayList<FoodLocal> foodlocals){
        super(context, R.layout.listview_result_view, foodlocals);
        this.foodlocals = foodlocals;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView;
        LayoutInflater itemInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            itemView = itemInflater.inflate(R.layout.listview_result_view, parent, false);
        }
        else {
            itemView = convertView;
        }

        FoodLocal loc = foodlocals.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.listview_result_image);
        switch (loc.getType()) {
            case "Oriental":
                imageView.setImageResource(R.mipmap.ic_oriental);
                break;
            case "Take Away":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Italiano":
                imageView.setImageResource(R.mipmap.ic_italiana);

                break;
            case "Hamburgueseria":
                imageView.setImageResource(R.mipmap.ic_hamburger);
                break;
            case "Restaurante":
                imageView.setImageResource(R.mipmap.ic_launcher);

                break;
            case "Nepali":
                imageView.setImageResource(R.mipmap.ic_launcher);

                break;
            case "Frankfurt":
                imageView.setImageResource(R.mipmap.ic_launcher);

                break;
            case "Cafe":
                imageView.setImageResource(R.mipmap.ic_cafe);
                break;
            case "Braseria":
                imageView.setImageResource(R.mipmap.ic_launcher);

                break;
            case "Tapas":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Mejicano":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Asador":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Marisqueria":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Bar":
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            default:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;

        }

        TextView locationNameTextView = (TextView) itemView.findViewById(R.id.listview_result_locationName);
        locationNameTextView.setText(loc.getName());

        RatingBar ratingBar = (RatingBar) itemView.findViewById(R.id.listview_result_starsBar);
        ratingBar.setNumStars((int)Math.round(loc.getRating()));

        TextView locationAddressTextView = (TextView) itemView.findViewById(R.id.listview_result_locationAddress);
        locationAddressTextView.setText(loc.getAddress());

        return itemView;
    }
}