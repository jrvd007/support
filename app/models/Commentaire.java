package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Commentaire extends Model {
    public String text;
    public Date date;
    public Usager usager;
    public Commentaire(String text, Usager usager) {
        this.text = text;
        this.usager = usager;
        this.date = new Date();
    }
}
