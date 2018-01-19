package demo.app.web.test;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public abstract class TestNGWebIntegrationTest extends AbstractTestNGSpringContextTests implements WebIntegrationTest {

    @Override
    public String getTestEngine() {
        return "testng";
    }
}
