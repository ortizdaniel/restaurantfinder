package pprog2.salleurl.edu.practica_pprog2.model;

/**
 * Created by David on 23/05/2017.
 */

public class Comment {
    private String commment;
    private String user;

    public Comment(String commment, String user){
        this.commment = commment;
        this.user = user;
    }

    public String getCommment() {
        return commment;
    }

    public void setCommment(String commment) {
        this.commment = commment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
