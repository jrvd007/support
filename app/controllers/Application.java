package controllers;

import play.*;
import play.mvc.*;

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

    public static void toutes() {
        index(Requete.findAll());
    }

    public static void mes() {
        index(Requete.find("byCreateur", user).fetch());
    }

    public static void assignees() {
        index(Requete.find("byResponsable", user).fetch());
    }

    public static void nonAssignees() {
        index(Requete.find("byResponsableIsNull").fetch());
    }
}
