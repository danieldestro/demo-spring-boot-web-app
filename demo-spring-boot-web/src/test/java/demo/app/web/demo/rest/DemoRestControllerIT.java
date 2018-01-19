package demo.app.web.demo.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import demo.app.demo.data.DemoRepository;
import demo.app.demo.domain.Demo;
import demo.app.web.test.BaseCrudRestIntegrationTest;

public class DemoRestControllerIT extends BaseCrudRestIntegrationTest<Demo, Integer> {

    private static final Logger  LOG          = LoggerFactory.getLogger(DemoRestControllerIT.class);

    private static final int     MAX_ROWS     = 10;

    private static final String  URI          = "/api/demo/security/";

    private static final Integer ID_NOT_FOUND = 99999999;

    @Autowired
    private DemoRepository       repository;

    @Override
    protected int countAll() {
        int totalCount = (int) repository.count();
        if (totalCount > MAX_ROWS) {
            totalCount = MAX_ROWS;
        }
        return totalCount;
    }

    @Test
    public void findAll() throws Exception {

        testFindAll(URI, 1, true,
                jsonPath("$.content[0].name").value("Demo 1"));
    }

    @Test
    public void find() {

        final Integer id = 1;
        String restUri = URI + id;
        Demo entity = testFindOne(restUri, id, Demo.class).getBody();
        assertEquals(entity.getName(), "Demo 1");
    }

    @Test
    public void find_not_found() {

        String restUri = URI + ID_NOT_FOUND;
        testFindNotFound(restUri, Demo.class);
    }

    @Test
    public void create() throws Exception {

        final String name = "Demo Test";

        Demo vo = new Demo();
        vo.setName(name);

        Demo entity = testCreate(URI, vo, Demo.class).getBody();

        assertEquals(entity.getName(), name);
    }

    @Test
    public void update() throws Exception {

        final Integer id = 3;
        final String name = "Demo Test Updated";

        Demo vo = new Demo();
        vo.setId(id);
        vo.setName(name);

        Demo entity = testUpdate(URI, vo, Demo.class).getBody();

        assertEquals(entity.getId(), id);
        assertEquals(entity.getName(), name);

        String restUri = URI + id;
        entity = testFindOne(restUri, id, Demo.class).getBody();

        assertEquals(entity.getName(), name);
    }

    @Test
    public void update_not_found() throws Exception {

        final String name = "Demo Update Not Found";

        Demo vo = new Demo();
        vo.setId(ID_NOT_FOUND);
        vo.setName(name);

        Demo entity = testUpdate(URI, vo, Demo.class).getBody();

        assertNotEquals(entity.getId(), ID_NOT_FOUND);
        assertEquals(entity.getName(), name);

        Integer id = entity.getId();
        String restUri = URI + id;
        entity = testFindOne(restUri, id, Demo.class).getBody();

        assertEquals(entity.getId(), id);
        assertEquals(entity.getName(), name);
    }

    @Test
    public void delete() throws Exception {

        Demo vo = new Demo();
        vo.setName("Demo To Delete");

        Demo entity = testCreate(URI, vo, Demo.class).getBody();

        final String restUri = URI + entity.getId();
        testDelete(restUri, vo, Demo.class);
    }

    @Test
    public void delete_not_found() throws Exception {

        final String restUri = URI + ID_NOT_FOUND;
        testDeleteNotFound(restUri);
    }
}
