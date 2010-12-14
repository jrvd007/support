package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

/*
 * Classe Usager.
 * Contient tous les attributs de l'usager (nom, prenom, etc.) ainsi qu'une méthode pour s'authentifier.
 */
@Entity
public class Usager extends Model {
	// Attributs.
    public String nom;
    public String prenom;
    public String telephone;
    public String courriel;
    public String bureau;
    public String username;
    public String password;

    // Constructeur.
    public Usager(String nom, String prenom, String telephone, String courriel, String bureau, String username, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.courriel = courriel;
        this.bureau = bureau;
        this.username = username;
        this.password = password;
    }

    // Méthode pour l'autentification.
    // Retourne l'usager si l'authentification est réussie, null sinon.
    public static Usager login(String username, String password) {
        return find("byUsernameAndPassword", username, password).first();
    }
    
    // Méthode pour s'avoir si l'usager est un technicien (retourne faux).
    // (Méthode redéfinie dans la sous-classe Technicien (où elle retourne vrai).
    public boolean isTech(){
    	return false;
    }
}
