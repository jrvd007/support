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
            user = Usager.find("byUsername", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }

    private static void index(List requetes) {
        render("Application/index.html", requetes);
    }

    public static void mes() {
        index(Requete.parCreateur(user));
    }

    public static void assignees() {
        index(Requete.assignees(user));
    }

    public static void nonAssignees() {
        index(Requete.nonAssignees());
    }
	public static void nouvelleRequete(){
		render();
	}

	public static void creerRequete(@Required String categorie, @Required String sujet, @Required String description){
		if(validation.hasErrors() || categorie.equals(""))	{
			flash.error("Vous avez omis de remplir certains champs!");
			nouvelleRequete();
			return;
		}
		Requete req = new Requete(user, Enum.valueOf(Requete.Categorie.class, categorie), sujet, description);
		req.save();
		mes();
	}
}
