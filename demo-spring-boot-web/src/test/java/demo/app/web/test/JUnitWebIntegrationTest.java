package demo.app.web.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class JUnitWebIntegrationTest implements WebIntegrationTest {

    @Override
    public String getTestEngine() {
        return "junit";
    }
}
