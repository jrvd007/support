package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
        return Usager.login(username, password) != null;
    }

    static boolean check(String profile) {
        if("isTechnicien".equals(profile)) {
            return Technicien.class.isInstance(
                Usager.find("byUsername", connected()).<Usager>first());
        }
        return false;
    }
}
