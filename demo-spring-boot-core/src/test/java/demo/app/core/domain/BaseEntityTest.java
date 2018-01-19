package demo.app.core.domain;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Objects;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.app.manager.domain.Application;
import demo.app.manager.domain.GlobalConfiguration;

public class BaseEntityTest {

    private static final Integer ID = 10;

    private BaseEntity<?>        entity;

    @BeforeTest
    public void init() {
        Application app = new Application();
        app.setId(ID);
        entity = app;
    }

    @Test
    public void test_getId() {
        assertNotNull(entity.getId());
    }

    @Test
    public void test_hashCode() {
        assertEquals(entity.hashCode(), Objects.hash(ID));
    }

    @Test
    public void test_equals() {
        Application app = new Application();
        app.setId(ID);

        assertEquals(entity, app);
    }

    @Test
    public void test_equals_diff_id() {
        Application app = new Application();
        app.setId(111);

        assertNotEquals(entity, app);
    }

    @Test
    public void test_equals_diff_entity() {
        GlobalConfiguration other = new GlobalConfiguration();
        other.setId(ID);

        assertNotEquals(entity, other);
    }

    @Test
    public void test_equals_diff_entity_diff_id() {
        GlobalConfiguration other = new GlobalConfiguration();
        other.setId(101);

        assertNotEquals(entity, other);
    }

    @Test
    public void test_toString() {
        String str = entity.toString();
        assertEquals(str.substring(0, str.indexOf('@')), "demo.app.manager.domain.Application");
        assertEquals(str.substring(str.indexOf('[')), "[id=10,name=<null>,description=<null>,url=<null>]");

        GlobalConfiguration other = new GlobalConfiguration();
        other.setId(ID);
        str = other.toString();
        assertEquals(str.substring(0, str.indexOf('@')), "demo.app.manager.domain.GlobalConfiguration");
        assertEquals(str.substring(str.indexOf('[')), "[id=10]");
    }

}
