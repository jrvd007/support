package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Commentaire extends Model {
    public String text;
    public Date date;
    public Commentaire(String text) {
        this.text = text;
        this.date = new Date();
    }
}
