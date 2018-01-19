package demo.app.core.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class JUnitBaseIntegrationTest implements IntegrationTest {

    @Override
    public String getTestEngine() {
        return "junit";
    }
}
