package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            Usager user = Usager.find("byUsername", Security.connected())
                                .first();
            renderArgs.put("user", user);
            renderArgs.put("isTech", Technicien.class.isInstance(user));
        }
    }

    public static void index() {
        render();
    }

	public static void showRequetes(){
		List requetes = Requete.findAll();
		render(requetes);
	}
	
	public static void pageCreerRequete(){
		render();
	}

	public static void creerRequete(String sujet, String description){
		long id = Requete.count();
		render();
	}
}
