package demo.app.core.test;

import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

public abstract class TestNGBaseIntegrationTest extends AbstractTransactionalTestNGSpringContextTests implements IntegrationTest {

    @Override
    public String getTestEngine() {
        return "testng";
    }
}
