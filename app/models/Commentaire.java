package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

/*
 * Classe représentant un commentaire 
 * Contient
 */
@Entity
public class Commentaire extends Model {
    public String text;
    public Date date;
    public Usager createur;
    public Commentaire(String text, Usager createur) {
        this.text = text;
        this.createur = createur;
        this.date = new Date();
    }
}
