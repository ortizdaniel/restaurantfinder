package pprog2.salleurl.edu.practica_pprog2.repositories;

import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

/**
 * Created by Mundirisa on 16/05/2017.
 */

public interface LocationsRepo {
    List<FoodLocal> search(String search);
}
