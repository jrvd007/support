package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Requete extends Model {
	public static enum Categorie{
		Logiciel, Systeme, Général, Autre 
	}
	public static final String Non_Assignee = "Non-assignée", En_Traitement = "En traitement", Succes = "Succes", Abandon = "Abandon"; 
	
    @ManyToOne
    public Usager createur;

    @ManyToOne
    public Usager responsable = null;

    public String sujet;

    @Lob
    public String description;

    public Date creation;
    
    public String categorie;
    public String statut;

    public Requete(Usager createur, String categorie, String sujet, String description) {
    	this.categorie = categorie;
    	this.statut = Non_Assignee;
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
