package controllers;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import models.Commentaire;
import models.Fichier;
import models.Requete;
import models.Technicien;
import models.Usager;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

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
        LinkedHashMap<String, String> urlmap = new LinkedHashMap();
        urlmap.put(Router.reverse("Application.mes").url, "Mes requêtes");
        urlmap.put(Router.reverse("Application.assignees").url, "Mes assignations");
        urlmap.put(Router.reverse("Application.nonAssignees").url, "Non assignées");
        render("Application/list.html", requetes, urlmap);
    }

    public static void mes() {
        list(Requete.parCreateur(user));
    }

    @Check("isTechnicien")
    public static void assignees() {
        list(Requete.parResponsable(user));
    }

    @Check("isTechnicien")
    public static void nonAssignees() {
        list(Requete.nonAssignees());
    }

    @Check("isTechnicien")
	public static void finaliserRequete(@Required long requete_id){
    	Requete requete = Requete.findById(requete_id);
		requete.finaliser();
		assignees();
	}

    @Check("isTechnicien")
	public static void abandonnerRequete(@Required long requete_id){
    	Requete requete = Requete.findById(requete_id);
		requete.abandonner();
		assignees();
	}

    @Check("isTechnicien")
	public static void assignerRequete(@Required long requete_id){
    	Requete requete = Requete.findById(requete_id);
		requete.assignerTech((Technicien) user);
		nonAssignees();
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

	public static void creerRequete(@Required String categorie, @Required String sujet, @Required String description, List<File> files){
		if(validation.hasErrors())	{
            params.flash();
            validation.keep();
			nouvelleRequete();
		}

		Requete req = new Requete(user, Enum.valueOf(Requete.Categorie.class, categorie), sujet, description);

        for (File newFile : files) {
            if (newFile != null) {
                Fichier fichier = new Fichier();
                fichier.save();
                File destdir = new File(Play.applicationPath + "/uploads/" + fichier.id);
                destdir.mkdir();

                // Move the temporary file to a permanent location
                File dest = new File(destdir.getPath() + "/" + newFile.getName());
                newFile.renameTo(dest);

                fichier.setFile(dest);
                fichier.save();

                req.addFile(fichier);
            }
        }

		req.save();
		mes();
	}

    public static void commentaire(@Required long requete_id, @Required String commentaireText) {
        Requete req = Requete.findById(requete_id);
        req.addCommentaire(new Commentaire(commentaireText, user));
        mes();
    }

    @Check("isTechnicien")
	public static void changerCategorie(@Required long requete_id, @Required String nouvcategorie) {
		Requete req = Requete.findById(requete_id);
		req.categorie = Enum.valueOf(Requete.Categorie.class, nouvcategorie);
		req.save();
		mes();
	}

    public static void download(@Required long requete_id, @Required long fichier_id){

    }

    public static void upload(@Required long requete_id, @Required File newFile) {
    	if(validation.hasErrors()){
    		params.flash();
            validation.keep();
            mes();
    	}
        Requete req = Requete.findById(requete_id);
        Fichier fichier = new Fichier();
        fichier.save(); // HACK - generate an id. See note in models/Fichier.java

        // Create a separate directory for this upload
        // Allows multiple uploads with same filename
        File destdir = new File(Play.applicationPath + "/uploads/" + fichier.id);
        destdir.mkdir();

        // Move the temporary file to a permanent location
        File dest = new File(destdir.getPath() + "/" + newFile.getName());
        newFile.renameTo(dest);

        // Add the new file location to the request
        fichier.setFile(dest);
        fichier.save();
        req.addFile(fichier);

        mes();
    }
}
