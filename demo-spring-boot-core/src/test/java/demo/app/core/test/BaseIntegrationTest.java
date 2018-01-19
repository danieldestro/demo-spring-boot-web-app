package demo.app.core.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import demo.app.admin.core.CoreSpringBootTestStarter;

/**
 * Base Spring Boot Integration Test class. All Integration Test classes must extends this class. <br>
 * This class can be configured to work as a TestNG test suite runtime or a JUnit flavor. <br>
 * Don't forget to use the correspondent @Test annotation according to the test engine chosen. <br>
 * https://spring.io/guides/gs/multi-module/
 */
@SpringBootTest(classes = CoreSpringBootTestStarter.class)
public abstract class BaseIntegrationTest
        // if you are going the TestNG way
        extends TestNGBaseIntegrationTest {
    // if you are going the JUnit way
    // extends JUnitBaseTestConfig {

    @Test
    public void contextLoads() {
    }
}
