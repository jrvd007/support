package models;

import javax.persistence.*;
import java.io.*;

import play.mvc.*;

import play.db.jpa.*;

@Entity
public class Fichier extends Model {
    public static File file;

    /* The hack below allows us to generate an id for our Fichier and _then_
     * add the actual file to it. There is probably a better way to do this. */
    public Fichier() {}
    public Fichier(File file) {
        this.file = file;
    }
}
