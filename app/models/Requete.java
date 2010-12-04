package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Requete extends Model {
    @ManyToOne
    public Usager createur;

    @ManyToOne
    public Usager responsable;

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

    public static List<Requete> assignee() {
        return find("byResponsableIsNotNull").fetch();
    }

    public static List<Requete> nonAssignee() {
        return find("byResponsableIsNull").fetch();
    }
}
