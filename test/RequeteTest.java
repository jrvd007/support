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
    }

    @Test
    public void retrieveAssignees() {
        List<Requete> assignees = Requete.assignee();
        assertEquals(assignees.size(), 2);
    }

    @Test
    public void retrieveNonAssignees() {
        List<Requete> nonAssignees = Requete.nonAssignee();
        assertEquals(nonAssignees.size(), 1);
    }
}

