package demo.app.web.manager.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import demo.app.manager.data.ApplicationRepository;
import demo.app.web.test.BaseCrudRestIntegrationTest;
import demo.app.web.vo.ApplicationVO;

public class ApplicationRestControllerIT extends BaseCrudRestIntegrationTest<ApplicationVO, Integer> {

    private static final int      MAX_ROWS     = 10;

    private static final String   URI          = "/api/manager/applications/";

    private static final Integer  ID_NOT_FOUND = 99999999;

    @Autowired
    private ApplicationRepository repository;

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
                jsonPath("$.content[0].name").value("Casper DC"));
    }

    @Test
    public void find() {

        final Integer id = 1;
        String restUri = URI + id;
        ApplicationVO entity = testFindOne(restUri, id, ApplicationVO.class).getBody();
        assertEquals(entity.getName(), "Casper DC");
    }

    @Test
    public void find_not_found() {

        String restUri = URI + ID_NOT_FOUND;
        testFindNotFound(restUri, ApplicationVO.class);
    }

    @Test
    public void create() throws Exception {

        final String name = "Casper Test";

        ApplicationVO vo = new ApplicationVO();
        vo.setName(name);
        vo.setDescription("Created by test automation");
        vo.setUrl("http://www.hpe.com/");

        ApplicationVO entity = testCreate(URI, vo, ApplicationVO.class).getBody();

        assertEquals(entity.getName(), name);
    }

    @Test
    public void create_error_validation() throws Exception {

        final String name = "ab";

        ApplicationVO vo = new ApplicationVO();
        vo.setName(name);
        vo.setDescription("Created by test automation");
        vo.setUrl("http://www.hpe.com/");

        testCreateErrorValidation(URI, vo, ApplicationVO.class);
    }

    @Test
    public void update() throws Exception {

        final Integer id = 5;
        final String name = "Casper Test Updated";

        ApplicationVO vo = new ApplicationVO();
        vo.setId(id);
        vo.setName(name);
        vo.setDescription("Updated by test automation");
        vo.setUrl("http://www.hpe.com/casper/updated/");

        ApplicationVO entity = testUpdate(URI, vo, ApplicationVO.class).getBody();

        assertEquals(entity.getId(), id);
        assertEquals(entity.getName(), name);

        String restUri = URI + id;
        entity = testFindOne(restUri, id, ApplicationVO.class).getBody();

        assertEquals(entity.getName(), name);
    }

    @Test
    public void update_not_found() throws Exception {

        final String name = "Casper Test Update Not Found";

        ApplicationVO vo = new ApplicationVO();
        vo.setId(ID_NOT_FOUND);
        vo.setName(name);
        vo.setDescription("Updated by test automation");
        vo.setUrl("http://www.hpe.com/casper/updated/");

        ApplicationVO entity = testUpdate(URI, vo, ApplicationVO.class).getBody();

        assertNotEquals(entity.getId(), ID_NOT_FOUND);
        assertEquals(entity.getName(), name);

        Integer id = entity.getId();
        String restUri = URI + id;
        entity = testFindOne(restUri, id, ApplicationVO.class).getBody();

        assertEquals(entity.getId(), id);
        assertEquals(entity.getName(), name);
    }

    @Test
    public void update_error_validation() throws Exception {

        final Integer ID = 5;
        final String name = "abc";

        ApplicationVO vo = new ApplicationVO();
        vo.setId(ID);
        vo.setName(name);
        vo.setDescription("Updated by test automation");
        vo.setUrl("http://www.hpe.com/casper/updated/");

        testUpdateErrorValidation(URI, vo, ApplicationVO.class);
    }

    @Test
    public void delete() throws Exception {

        ApplicationVO vo = new ApplicationVO();
        vo.setName("Application to Delete");
        vo.setDescription("Created by test automation");
        vo.setUrl("http://www.hpe.com/");

        ApplicationVO entity = testCreate(URI, vo, ApplicationVO.class).getBody();

        final String restUri = URI + entity.getId();
        testDelete(restUri, vo, ApplicationVO.class);
    }

    @Test
    public void delete_not_found() throws Exception {

        final String restUri = URI + ID_NOT_FOUND;
        testDeleteNotFound(restUri);
    }
}
