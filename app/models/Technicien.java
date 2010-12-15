package models;

import javax.persistence.*;

/*
 * Classe Technicien.
 * Extends Usager. Méthode isTech() ajoutée pour permettre la différenciation.
 */
@Entity
public class Technicien extends Usager {
	// Contructeur.
    public Technicien(String nom, String prenom, String telephone, String courriel, String bureau, String username, String password) {
        super(nom, prenom, telephone, courriel, bureau, username, password);
    }
    
    // Méthode pour s'avoir si l'usager est un technicien (retourne vrai).
    public boolean isTech(){
    	return true;
    }
}
