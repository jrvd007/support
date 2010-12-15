package controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import play.db.jpa.JPABase;

import models.Requete;
import models.Technicien;

/*
 * Classe pour générer un rapport
 * Toutes les informations devant apparaitre dans le rapport sont contenus dans cette classe.
 */
public class Rapport{
	// Génère un rapport et le retourne
	public static Rapport genere(){
		return new Rapport();
	}
	
	// Constructeur
	private Rapport(){
		assignees = Requete.count("byStatut", Requete.Statut.Assignée);
		nonAssignees = Requete.count("byResponsableIsNull");
		completees = Requete.count("byStatut", Requete.Statut.Complétée);
		abandonnees = Requete.count("byStatut", Requete.Statut.Abandonnée);
		
		techs = new LinkedList<TechStats>();
		for(JPABase tech : Technicien.findAll()){
			techs.add(new TechStats((Technicien) tech));
		}
		
		assignees = 32;
		nonAssignees = 12;
		completees = 104;
		abandonnees = 11;
	}
	
	// Statistiques globales
	public long assignees;
	public long nonAssignees;
	public long completees;
	public long abandonnees;
	
	// Liste contenant les stats de chaque technicien
	List<TechStats> techs;
	
	// Classe contenant les statistiques pour un technicien
	public class TechStats{
		// Constructeur
		public TechStats(Technicien tech){
			nom = tech.prenom + " "+ tech.nom + "  ("+tech.username+")";
			entraitement = 5;//Requete.count("byResponsableAndStatut", tech, Requete.Statut.Assignée);
			completees = 16; //Requete.count("byResponsableAndStatut", tech, Requete.Statut.Complétée);
			abandonnees = 1; //Requete.count("byResponsableAndStatut", tech, Requete.Statut.Abandonnée);
			
			categories = new LinkedList<CatStats>();
			for(Requete.Categorie cat : Requete.Categorie.values()){
				categories.add(new CatStats(cat, 4 /*Requete.count("byResponsableAndStatutAndCategorie", tech, Requete.Statut.Complétée, cat)*/));
			}
		}
		// Statistiques du tech
		public String nom;
		public long entraitement;
		public long completees;
		public long abandonnees;
		
		// Liste contenant les stats pour chaque catégorie
		public List<CatStats> categories;
		
		// Classe contenant des stats sur les requêtes traitées par catégories
		public class CatStats{
			// Constructeur.
			public CatStats(Requete.Categorie categorie, long count){
				this.nom = categorie.toString();
				this.count = count;
			}
			// Stats sur la catégorie.
			public String nom;
			public long count;
		}
	}
}
