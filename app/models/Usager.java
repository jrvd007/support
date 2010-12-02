package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Usager extends Model {
    public String nom;
    public String prenom;
    public String telephone;
    public String courriel;
    public String bureau;
    public String username;
    public String password;

    public Usager(String nom, String prenom, String telephone,
            String courriel, String bureau, String username,
            String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.courriel = courriel;
        this.bureau = bureau;
        this.username = username;
        this.password = password;
    }
}
