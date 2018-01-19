package demo.app.web.test;

import static demo.app.web.test.CustomSecurityMockMvcRequestPostProcessors.httpBasicUserAdmin;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultMatcher;

import demo.app.core.domain.BaseIdentifiable;
import demo.app.demo.domain.Demo;

/**
 * Base Spring Boot Integration Test class for CRUD Rest Services. All Integration Test classes for CRUD Rest Services must extends this class. <br>
 * This class can be configured to work as a TestNG test suite runtime or a JUnit flavor. <br>
 * Don't forget to use the correspondent @Test annotation according to the test engine chosen.
 */
public abstract class BaseCrudRestIntegrationTest<T, K> extends BaseRestIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseCrudRestIntegrationTest.class);

    /**
     * Returns the row count of the specific entity.
     * 
     * @return
     */
    protected abstract int countAll();

    /**
     * Test a find all rest call and check the json response.
     * 
     * @param restUri
     *            rest api URI
     * @param id
     *            id to test the very first occurrence of the json response content
     * @param name
     *            name to test the very first occurrence of the json response content
     * @param first
     *            indicates this should be the first page returned
     * @return json response in flat string format
     * @throws Exception
     *             in case any error occur
     */
    protected String testFindAll(String restUri, K id, boolean first, ResultMatcher... status) throws Exception {

        int count = countAll();

        ResultMatcher[] matchers = {
                jsonPath("$.first").value(Boolean.toString(first)),
                jsonPath("$.content", hasSize(count)),
                jsonPath("$.content[0].id").value(id)
        };

        if (status != null) {
            matchers = Stream.concat(Arrays.stream(matchers), Arrays.stream(status)).toArray(ResultMatcher[]::new);
        }

        String json = performApiGet(restUri, httpBasicUserAdmin(), matchers);

        assertJsonResponse(json);

        return json;
    }

    protected ResponseEntity<T> testFindOne(String restUri, K id, Class<T> type) {

        TestRestTemplate rest = getRestTemplateAuth();
        ResponseEntity<T> responseEntity = rest.getForEntity(restUri, type);
        T entity = responseEntity.getBody();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        BaseIdentifiable<?> identifiable = (BaseIdentifiable<?>) entity;
        assertEquals(identifiable.getId(), id);

        return responseEntity;
    }

    protected ResponseEntity<T> testFindNotFound(String restUri, Class<T> type) {

        TestRestTemplate rest = getRestTemplateAuth();
        ResponseEntity<T> responseEntity = rest.getForEntity(restUri, type);
        T entity = responseEntity.getBody();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(entity);

        return responseEntity;
    }

    protected ResponseEntity<T> testCreate(String restUri, T entity, Class<T> type) throws Exception {

        ResponseEntity<T> response = testCreate(restUri, entity, type, HttpStatus.OK);
        T responseEntity = response.getBody();

        BaseIdentifiable<?> identifiable = (BaseIdentifiable<?>) responseEntity;
        assertNotNull(identifiable.getId());

        return response;
    }

    protected ResponseEntity<T> testCreateErrorValidation(String restUri, T entity, Class<T> type) throws Exception {

        return testCreate(restUri, entity, type, HttpStatus.PRECONDITION_FAILED);
    }

    private ResponseEntity<T> testCreate(String restUri, T entity, Class<T> type, HttpStatus statusCode) throws Exception {

        HttpEntity<T> httpEntity = buildHttpEntity(entity, true, false);

        TestRestTemplate rest = getRestTemplateAuth();
        ResponseEntity<T> response = rest.postForEntity(restUri, httpEntity, type);

        assertEquals(response.getStatusCode(), statusCode);

        return response;
    }

    protected ResponseEntity<T> testUpdate(String restUri, T entity, Class<T> type) throws Exception {

        HttpEntity<T> httpEntity = buildHttpEntity(entity, true, true);

        TestRestTemplate rest = getRestTemplateAuth();
        // rest.put(restUri, httpEntity); // this method does not throw any error when something goes wrong
        ResponseEntity<T> response = rest.exchange(restUri, HttpMethod.PUT, httpEntity, type);
        T responseEntity = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        BaseIdentifiable<?> identifiable = (BaseIdentifiable<?>) responseEntity;
        assertNotNull(identifiable.getId());

        return response;
    }

    protected ResponseEntity<T> testUpdateErrorValidation(String restUri, T entity, Class<T> type) throws Exception {

        HttpEntity<T> httpEntity = buildHttpEntity(entity, true, true);

        TestRestTemplate rest = getRestTemplateAuth();
        // rest.put(restUri, httpEntity); // this method does not throw any error when something goes wrong
        ResponseEntity<T> response = rest.exchange(restUri, HttpMethod.PUT, httpEntity, type);

        assertEquals(response.getStatusCode(), HttpStatus.PRECONDITION_FAILED);

        return response;
    }

    protected void testDelete(String restUri, T entity, Class<T> type) throws Exception {

        HttpEntity<Demo> httpEntity = buildHttpEntity(null, true, true);

        TestRestTemplate rest = getRestTemplateAuth();
        // rest.delete(restUri); // this method does not throw any error when something goes wrong
        ResponseEntity<T> response = rest.exchange(restUri, HttpMethod.DELETE, httpEntity, type);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        ResponseEntity<T> responseEntity = rest.getForEntity(restUri, type);
        entity = responseEntity.getBody();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(entity);
    }

    protected void testDeleteNotFound(String restUri) throws Exception {

        HttpEntity<Demo> httpEntity = buildHttpEntity(null, true, true);

        TestRestTemplate rest = getRestTemplateAuth();
        ResponseEntity<String> response = rest.exchange(restUri, HttpMethod.DELETE, httpEntity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.PRECONDITION_FAILED);
        assertTrue(response.getBody().contains("\"defaultMessage\":\"No results found\""));
    }
}
