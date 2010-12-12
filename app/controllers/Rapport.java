package controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import play.db.jpa.JPABase;

import models.Requete;
import models.Technicien;

public class Rapport{
	public static Rapport genere(){
		return new Rapport();
	}
	
	private Rapport(){
		assignees = Requete.count("byStatut", Requete.Statut.Assignée);
		nonAssignees = Requete.count("byResponsableIsNull");
		completees = Requete.count("byStatut", Requete.Statut.Completée);
		abandonnees = Requete.count("byStatut", Requete.Statut.Abandonnée);
		
		techs = new LinkedList<TechStats>();
		for(JPABase tech : Technicien.findAll()){
			techs.add(new TechStats((Technicien) tech));
		}
	}
	
	public long assignees;
	public long nonAssignees;
	public long completees;
	public long abandonnees;
	List<TechStats> techs;
	
	public class TechStats{
		public TechStats(Technicien tech){
			nom = tech.prenom + " "+ tech.nom + "  ("+tech.username+")";
			entraitement = Requete.count("byResponsableAndStatut", tech, Requete.Statut.Assignée);
			completees = Requete.count("byResponsableAndStatut", tech, Requete.Statut.Completée);
			abandonnees = Requete.count("byResponsableAndStatut", tech, Requete.Statut.Abandonnée);
			
			categories = new LinkedList<CatStats>();
			for(Requete.Categorie cat : Requete.Categorie.values()){
				categories.add(new CatStats(cat, Requete.count("byResponsableAndStatutAndCategorie", tech, Requete.Statut.Completée, cat)));
			}
		}
		public String nom;
		public long entraitement;
		public long completees;
		public long abandonnees;
		public List<CatStats> categories;
		
		public class CatStats{
			public CatStats(Requete.Categorie categorie, long count){
				this.nom = categorie.toString();
				this.count = count;
			}
			public String nom;
			public long count;
		}
	}
}
