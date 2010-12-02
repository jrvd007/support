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
                "J'ai vraiment besoin d'aide! Chais pas quoi faire!");

        Usager grinshpun = Usager.find("byNom", "Grinshpun").first();
        assertEquals(aidezmoi.createur, grinshpun);
    }
}

