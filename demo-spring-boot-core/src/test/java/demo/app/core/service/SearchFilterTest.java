package demo.app.core.service;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.app.core.service.SearchFilter;

public class SearchFilterTest {

    private SearchFilter filter;

    @BeforeTest
    public void init() {
        filter = new SearchFilter();
    }

    @Test
    public void isEmpty() {
        assertTrue(filter.isEmpty());
    }

    @Test
    public void isEmpty_not() {
        filter.setFilter("not empty");
        assertFalse(filter.isEmpty());
    }
}
