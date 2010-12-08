package controllers;

import play.*;
import play.mvc.*;

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
        }
    }

    public static void index() {
        List requetes = Requete.findAll();
        render(requetes);
    }

}
