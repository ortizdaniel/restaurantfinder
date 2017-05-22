package pprog2.salleurl.edu.practica_pprog2.repositories;

import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

/**
 * Created by Mundirisa on 22/05/2017.
 */

public interface FavoriteFoodLocalsRepo {
    List<FoodLocal> getFavoriteFoodLocals(int userId);
    void insertFavoriteFoodLocal(int userId, FoodLocal foodLocal);
}
