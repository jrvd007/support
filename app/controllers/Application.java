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

	public static void index(){
		List requetes = Requete.find("byCreateur", user).fetch();
		render(requetes);
	}
	
	public static void pageCreerRequete(){
		render();
	}

	public static void creerRequete(@Required String categorie, @Required String sujet, @Required String description){
		if(validation.hasErrors() || categorie.equals(""))	{
			flash.error("Vous avez omis de remplir certains champs!");
			pageCreerRequete();
			return;
		}
		Requete req = new Requete(user, categorie, sujet, description);
		req.save();
		index();
	}
}
