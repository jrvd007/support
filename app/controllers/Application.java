package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {
	private static Usager user;
	
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            user = Usager.find("byUsername", Security.connected())
                                .first();
            renderArgs.put("user", user);
        }
    }

	//public static void index(List requetes){
	//	if(requetes == null) requetes = Requete.find("byCreateur", user).fetch();
	//	render(requetes);
	//}
	
	public static void pageCreerRequete(String categorie, String sujet, String description){
		render(categorie, sujet, description);
	}

	public static void creerRequete(@Required String categorie, @Required String sujet, @Required String description){
		if(validation.hasErrors() || categorie.equals(""))	{
			flash.error("Vous avez omis de remplir certains champs!");
			pageCreerRequete(categorie, sujet, description);
			return;
		}
		Requete req = new Requete(user, categorie, sujet, description);
		req.save();
		index(null);
	}
	
	public static void index(String choice){
		List requetes = null;
		if(choice == null){
			if(user.isTech()) {
				requetes = Requete.findAll();
				choice = "toutsansfiltre";
			} else {
				requetes = Requete.find("byCreateur", user).fetch();
				choice = "sansfiltre";
			}
			render(requetes);
			return;
		}
		
		if (choice.contains("tout")){
			if(choice.equals("toutsansfiltre")){
				requetes = Requete.findAll();
			}else if(choice.contains("toutassigne")){
				requetes = Requete.assignee();
			}else if(choice.contains("toutnonassigne")){
				requetes = Requete.nonAssignee();
			}else if(choice.equals("toutsucces")){
				requetes = Requete.find("byStatus", Requete.Succes).fetch();
			}else if(choice.equals("toutabandon")){
				requetes = Requete.find("byStatus", Requete.Abandon).fetch();
			}
		}
		else{
			if(choice.equals("sansfiltre")){
				requetes = Requete.find("byCreateur", user).fetch();
			}else if(choice.equals("succes")){
				requetes = Requete.find("byCreateurAndStatus", user, Requete.Succes).fetch();
			}else if(choice.equals("abandon")){
				requetes = Requete.find("byCreateurAndStatus", user, Requete.Abandon).fetch();
			}else if(choice.equals("assigne")){
				requetes = Requete.find("byCreateurAndResponsableIsNotNull", user).fetch();
			}else if(choice.equals("nonassigne")){
				requetes = Requete.find("byCreateurAndResponsableIsNull", user).fetch();
			}
		}
		render(choice, requetes);
		return;
	}
	
	public static void afficheRequete(Long id)
	{
		Requete requete = Requete.find("byId",id).first();
		if(requete == null)
		{
			index(null);
			return;
		}
		else
		{
		render(requete);
		return;
		}
	}
}
