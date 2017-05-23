package pprog2.salleurl.edu.practica_pprog2.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.adapters.FoodLocalsAdapter;
import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;


public class ResultsListViewFragment extends Fragment {

    private ListView listView;
    private FoodLocalsAdapter adapter;
    private ArrayList<FoodLocal> foodLocals;
    private ArrayList<FoodLocal> allFoodLocals;

    public ResultsListViewFragment(){
        super();
        this.foodLocals = new ArrayList<>();
        this.allFoodLocals = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        // Recuperamos el componente gráfico para poder asignarle un adapter.
        listView = (ListView) view.findViewById(R.id.fragment_results_listview);

        // Creamos el adapter.
        adapter = new FoodLocalsAdapter(getActivity(), foodLocals);
        // Vinculamos el adapter a la ListView.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
        // Vinculamos el adapter (que implementa un OnItemClickListener) como Listener de cada
        // uno de los items de la ListView.

        return view;
    }

    public void newLocations(ArrayList<FoodLocal> foodLocals) {
        allFoodLocals = foodLocals;
        this.foodLocals.clear();
        this.foodLocals.addAll(foodLocals);
    }


    public void filterList(String type, Boolean onlyOpen) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        foodLocals.clear();
        for (FoodLocal l : allFoodLocals) {
            if (onlyOpen) {
                try {
                    if (isTimeBetweenTwoTime(l.getOpeningHour(), l.getClosingHour(), dateFormat.format(date))) {
                        if (type.equals(getString(R.string.all_types))) {
                            foodLocals.add(l);
                        } else if (type.equals(l.getType())) {
                            foodLocals.add(l);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (type.equals("ALL")) {
                    foodLocals.add(l);
                } else if (type.equals(l.getType())) {
                    foodLocals.add(l);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            java.util.Date inTime = new SimpleDateFormat("HH:mm").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            java.util.Date checkTime = new SimpleDateFormat("HH:mm").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            return false;
        }
    }
}