package pprog2.salleurl.edu.practica_pprog2.repositories;

import pprog2.salleurl.edu.practica_pprog2.model.User;

/**
 * Created by David on 22/05/2017.
 */

public interface UsersRepo {
    void addUser(String nombre,String  apellidos,String image_path,String description,String email,char sexo);
    User getUser(String username, String password);
    void updateUser();
}
