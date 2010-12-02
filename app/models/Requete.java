package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Requete extends Model {
    @ManyToOne
    public Usager createur;

    public String sujet;

    @Lob
    public String description;

    public Date creation;

    public Requete(Usager createur, String sujet, String description) {
        this.createur = createur;
        this.sujet = sujet;
        this.description = description;
        this.creation = new Date();
    }
}
