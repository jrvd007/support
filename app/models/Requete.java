package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Requete extends Model {
	public static enum Categorie {
		Logiciel, Système, Général, Autre
	}

    @ManyToOne
    public Usager createur;

    @ManyToOne
    public Usager responsable;

    public String sujet;

    @Lob
    public String description;

    public Date creation;

    public Categorie categorie;

    public Boolean ouvert;

    public Requete(Usager createur, Categorie categorie, String sujet, String description) {
    	this.categorie = categorie;
    	this.ouvert = true;
        this.createur = createur;
        this.sujet = sujet;
        this.description = description;
        this.creation = new Date();
    }

    public static List<Requete> parCreateur(Usager u) {
        return find("byCreateur", u).fetch();
    }

    public static List<Requete> assignees(Usager u) {
        return find("byResponsable", u).fetch();
    }

    public static List<Requete> nonAssignees() {
        return find("byResponsableIsNull").fetch();
    }
}
