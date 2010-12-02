import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UsagerTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }

    @Test
    public void retrieveUsager() {
        Usager grinshpun = Usager.find("byNom", "Grinshpun").first();

        assertNotNull(grinshpun);
        assertEquals("Matthew", grinshpun.prenom);
    }

    @Test
    public void tryLogin() {
        assertNotNull(Usager.login("grinshpun", "pass"));
        assertNull(Usager.login("grinshpun", "random"));
    }
}
