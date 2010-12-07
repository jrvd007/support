package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Requete extends Model {
	static enum Categorie{
		Logiciel, Systeme, Général, Autre 
	}
	static enum Status{
		Non_Assignee, Assignee, Abandon, Succes
	}
	
    @ManyToOne
    public Usager createur;

    @ManyToOne
    public Usager responsable;

    public String sujet;

    @Lob
    public String description;

    public Date creation;
    
    Categorie categorie;
    Status status;

    public Requete(Usager createur, Categorie categorie, String sujet, String description) {
    	this.categorie = categorie;
    	this.status = Status.Non_Assignee;
        this.createur = createur;
        this.sujet = sujet;
        this.description = description;
        this.creation = new Date();
    }

    public static List<Requete> assignee() {
        return find("byResponsableIsNotNull").fetch();
    }

    public static List<Requete> nonAssignee() {
        return find("byResponsableIsNull").fetch();
    }
}
