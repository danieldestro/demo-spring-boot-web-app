package demo.app.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.Test;

import demo.app.core.test.BaseIntegrationTest;
import demo.app.manager.domain.Application;
import demo.app.manager.service.ApplicationService;

/**
 * Example of an Integration Test for a Crud Service implementation for a specific entity.
 */
public class ApplicationServiceIT extends BaseIntegrationTest {

    private static final int   ID_NOT_FOUND = 999999999;

    @Autowired
    private ApplicationService appService;

    @Test
    public void findAll() {
        List<Application> apps = appService.findAll();

        assertNotNull(apps);
        assertTrue(apps.size() > 0);
    }

    @Test
    public void findById() {
        Integer id = 1;
        Application app = appService.findById(id);

        assertNotNull(app);
        assertEquals(app.getId(), id);
    }

    @Test
    public void findById_not_found() {
        Application app = appService.findById(ID_NOT_FOUND);

        assertNull(app);
    }

    @Test
    public void findByName() {
        List<Application> apps = appService.findByName("Demo");

        assertNotNull(apps);
        assertTrue(apps.size() > 0);
    }

    @Test
    public void findByName_empty() {
        List<Application> apps = appService.findByName("uyd2763tT&^T&^e3y");

        assertNotNull(apps);
        assertEquals(apps.size(), 0);
    }

    @Test
    public void findByName_pageable() {
        validate_findByName_pageable("Demo", 0);
        validate_findByName_pageable("Demo", 1);
    }

    private void validate_findByName_pageable(String search, int pageNumber) {
        Page<Application> page = appService.findByName(search, new PageRequest(pageNumber, 100));

        assertNotNull(page);
        assertTrue(page.getSize() > 0);
        assertTrue(page.getTotalElements() > 0);
        assertTrue(page.getTotalPages() > 0);
        assertTrue(pageNumber > 0 ? (page.getNumberOfElements() == 0) : (page.getNumberOfElements() > 0));
        assertEquals(page.getNumber(), pageNumber);

        List<Application> apps = page.getContent();

        assertNotNull(apps);
        assertTrue(pageNumber > 0 ? (apps.size() == 0) : (apps.size() > 0));
    }

    @Test
    public void crud_full_test() {
        Application entity = createEntity();
        entity = appService.create(entity);

        assertNotNull(entity);
        assertNotNull(entity.getId());

        Application app = appService.findById(entity.getId());
        assertEquals(app, entity);
        assertEquals(app.getDescription(), entity.getDescription());

        app.setDescription("Some other description here");
        app = appService.update(app);
        app = appService.findById(app.getId());
        assertEquals(app, entity);
        assertEquals(app.getDescription(), entity.getDescription());

        appService.delete(app);
        assertNotNull(app);
        app = appService.findById(app.getId());
        assertNull(app);
        assertNotNull(entity);
    }

    @Test
    public void update_not_existing_id() {
        Application app = createEntity();
        app.setId(ID_NOT_FOUND);
        app.setName("updated application");
        appService.update(app);
    }

    @Test
    public void delete() {
        int id = 4;
        appService.delete(id);
        Application app = appService.findById(id);
        assertNull(app);
    }

    // @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void delete_error_constraint() {
        int id = 1;
        appService.delete(id);
        Application app = appService.findById(id);
        assertNull(app);
    }

    @Test(expectedExceptions = EmptyResultDataAccessException.class)
    public void delete_entity_not_found() {
        Application app = createEntity();
        app.setId(ID_NOT_FOUND);
        appService.delete(app);
    }

    @Test(expectedExceptions = EmptyResultDataAccessException.class)
    public void delete_by_id_not_found() {
        appService.delete(ID_NOT_FOUND);
    }

    private Application createEntity() {
        Application entity = new Application();
        entity.setName("My Application");
        entity.setDescription("Some description here.");
        entity.setUrl("http://www.demo.com/");
        return entity;
    }
}
