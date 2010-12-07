import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class TechnicienTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }

    @Test
    public void retrieveTechnicien() {
        Technicien leon = Technicien.find("byPrenom", "Leon").first();

        assertNotNull(leon);
        assertEquals("Grynszpan", leon.nom);
    }

    @Test
    public void tryLogin() {
        assertNotNull(Usager.login("leon", "grynszpan"));
        assertNull(Usager.login("leon", "random"));
    }
}
