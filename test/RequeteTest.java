import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class RequeteTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }

    @Test
    public void retrieveRequete() {
        Requete aidezmoi = Requete.find("bySujet", "besoin d'aide").first();
        assertNotNull(aidezmoi);
        assertEquals(aidezmoi.description,
                "Mon ordi ne fonctionne plus.");

        Usager grinshpun = Usager.find("byNom", "Grinshpun").first();
        assertEquals(aidezmoi.createur, grinshpun);

        Requete byCreateur = Requete.find("byCreateur", grinshpun).first();
        assertEquals(aidezmoi, byCreateur);
    }

    @Test
    public void retrieveAssignees() {
        Usager vandoorn = Usager.find("byUsername", "vandoorn").first();
        List<Requete> assignees = Requete.parResponsable(vandoorn);
        assertEquals(assignees.size(), 1);
        // verify properties or add more?
    }

    @Test
    public void retrieveNonAssignees() {
        List<Requete> nonAssignees = Requete.nonAssignees();
        assertEquals(nonAssignees.size(), 1);
    }

    @Test
    public void getCommentaires() {
        Requete requete = Requete.find("bySujet", "Je suis perdue.").first();
        assertEquals(requete.commentaires.size(), 2);
        assertEquals(requete.commentaires.get(0).text, "Je suis toujours très perdue.");
    }
}

