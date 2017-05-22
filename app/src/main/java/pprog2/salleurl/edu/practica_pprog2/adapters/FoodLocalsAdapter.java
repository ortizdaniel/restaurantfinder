package pprog2.salleurl.edu.practica_pprog2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.activities.DescriptionActivity;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

/**
 * Created by Mundirisa on 16/05/2017.
 */
public class FoodLocalsAdapter extends ArrayAdapter<FoodLocal> implements AdapterView.OnItemClickListener {
    private ArrayList<FoodLocal> foodlocals;
    private Context context;

    public FoodLocalsAdapter(@NonNull Context context, ArrayList<FoodLocal> foodlocals){
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
        imageView.setImageResource(FoodLocal.getImageResourceId(loc.getType()));

        TextView locationNameTextView = (TextView) itemView.findViewById(R.id.listview_result_locationName);
        locationNameTextView.setText(loc.getName());

        RatingBar ratingBar = (RatingBar) itemView.findViewById(R.id.listview_result_starsBar);
        ratingBar.setRating((float)loc.getRating());

        TextView locationAddressTextView = (TextView) itemView.findViewById(R.id.listview_result_locationAddress);
        locationAddressTextView.setText(loc.getAddress());

        return itemView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("FOOD_LOCAL", foodlocals.get(position));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}