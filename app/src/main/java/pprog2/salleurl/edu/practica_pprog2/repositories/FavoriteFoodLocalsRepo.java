package pprog2.salleurl.edu.practica_pprog2.repositories;

import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.model.FoodLocal;

/**
 * Created by Mundirisa on 22/05/2017.
 */

public interface FavoriteFoodLocalsRepo {
    List<FoodLocal> getFavoriteFoodLocals(String userEmail);
    void insertFavoriteFoodLocal(String userEmail, FoodLocal foodLocal);
    void deleteFavoriteFoodLocal(String userEmail,FoodLocal foodLocal);
    boolean isFavorite(String userEmail, FoodLocal foodLocal);
}
