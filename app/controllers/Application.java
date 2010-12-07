package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Application extends Controller {

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
