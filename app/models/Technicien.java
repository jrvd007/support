package models;

import javax.persistence.*;

@Entity
public class Technicien extends Usager {
    public Technicien(String nom, String prenom, String telephone,
            String courriel, String bureau, String username,
            String password) {
        super(nom, prenom, telephone, courriel, bureau, username, password);
    }
    
    public boolean isTech(){
    	return true;
    }
}
