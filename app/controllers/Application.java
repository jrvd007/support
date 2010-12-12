package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    private static void list(List requetes) {
	renderArgs.put("categories", Requete.Categorie.values());
        System.out.println(request.path);
        SortedMap<String, String> urlmap = new TreeMap();
        urlmap.put(Router.reverse("Application.mes").url, "Mes requêtes");
        urlmap.put(Router.reverse("Application.assignees").url,
                   "Mes assignations");
        urlmap.put(Router.reverse("Application.nonAssignees").url,
                   "Non assignées");

        render("Application/list.html", requetes, urlmap);
    }

    public static void mes() {
        list(Requete.parCreateur(user));
    }

    @Check("isTechnicien")
    public static void assignees() {
        list(Requete.assignees(user));
    }

    @Check("isTechnicien")
    public static void nonAssignees() {
        list(Requete.nonAssignees());
    }

	public static void finalisereq()
	{
		mes();	
	}
	public static void abandonnereq()
	{
		mes();
	}
	public static void assignereq()
	{
		mes();
	}

	public static void nouvelleRequete(){
        renderArgs.put("categories", Requete.Categorie.values());
		render();
	}
	
	@Check("isTechnicien")
	public static void rapport(){
		renderArgs.put("rapport", Rapport.genere());
		render();	
	}

	public static void creerRequete(@Required String categorie, @Required String sujet, @Required String description){
		if(validation.hasErrors())	{
            params.flash();
            validation.keep();
			nouvelleRequete();
		}

		Requete req = new Requete(user, Enum.valueOf(Requete.Categorie.class, categorie), sujet, description);
		req.save();
		mes();
	}

	public static void afficheRequete(Long id)
	{
		Requete requete = Requete.find("byId",id).first();
		if(requete == null)
		{
			list(null);
			return;
		}
		else
		{
		render(requete);
		return;
		}
	}

    public static void commentaire(@Required long requete_id, @Required String commentaireText) {
        Requete req = Requete.findById(requete_id);
        req.addCommentaire(commentaireText);
        mes();
    }
    
    @Check("isTechnicien")
	public static void newcategorie(@Required long requete_id, @Required String nouvcategorie) {
		Requete req = Requete.findById(requete_id);
		req.categorie = Enum.valueOf(Requete.Categorie.class, nouvcategorie);
		req.save();
		mes();
	} 

    public static void upload(@Required long requete_id, @Required java.io.File newFile) {
        Requete req = Requete.findById(requete_id);
        Fichier fichier = new Fichier();
        fichier.save(); // HACK - generate an id. See note in models/Fichier.java
        System.out.println(fichier.id);
        System.out.println(newFile);

        /*
        //String location
        newFile.renameTo(publicplace);
        fichier.file(newFile);
        System.out.println(newFile);
        fichier.save();
        //req.addFile(newFile);
        */

        mes();
    }
}
