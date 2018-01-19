package demo.app.web.config;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.ObjectError;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import demo.app.core.util.MessageUtils;

public class GlobalExceptionHandlerTest {

    private static final String    TEST_MSG_EX1 = "column does not allow nulls";
    private static final String    TEST_KEY_EX1 = "err.data.column.not.null";

    private static final String    TEST_MSG_EX2 = "Violation of UNIQUE KEY constraint";
    private static final String    TEST_KEY_EX2 = "err.data.unique.key.violation";

    private static final String    TEST_MSG_EX3 = "DELETE statement conflicted with the REFERENCE constraint";
    private static final String    TEST_KEY_EX3 = "err.data.referential.integrity.constraint";

    private static final String    TEST_MSG_EX4 = "Some other error message";
    private static final String    TEST_KEY_EX4 = "err.org.hibernate.exception.ConstraintViolationException";

    @Mock
    private MessageUtils           messageUtilsMock;

    @InjectMocks
    private GlobalExceptionHandler handler      = new GlobalExceptionHandler();

    @BeforeClass
    public void initClass() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleDataIntegrityViolation_no_root_cause() {
        DataIntegrityViolationException ex = createDataIntegrityViolationException(null);

        runTest(ex, "Data Integrity Violation");
    }

    @Test
    public void handleDataIntegrityViolation_other_root_cause() {
        Exception rootex = new Exception("some root cause here");
        DataIntegrityViolationException ex = createDataIntegrityViolationException(rootex);

        runTest(ex, "Data Integrity Violation; nested exception is java.lang.Exception: some root cause here");
    }

    @Test
    public void handleDataIntegrityViolation_ConstraintViolationException() {
        handleDataIntegrityViolation(TEST_KEY_EX1, TEST_MSG_EX1);
        handleDataIntegrityViolation(TEST_KEY_EX2, TEST_MSG_EX2);
        handleDataIntegrityViolation(TEST_KEY_EX3, TEST_MSG_EX3);
        handleDataIntegrityViolation(TEST_KEY_EX4, TEST_MSG_EX4);
    }

    private void test(List<ObjectError> errors, String errMessage) {
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getDefaultMessage(), errMessage);
    }

    private void handleDataIntegrityViolation(String errKey, String errMessage) {
        when(messageUtilsMock.getMessage(errKey)).thenReturn(errMessage);

        DataIntegrityViolationException ex = createWithConstraintViolationException(errMessage);
        runTest(ex, errMessage);
    }

    private void runTest(DataIntegrityViolationException ex, String errMessage) {
        List<ObjectError> errors = handler.handleDataIntegrityViolation(ex);
        test(errors, errMessage);
    }

    private DataIntegrityViolationException createWithConstraintViolationException(String errMessage) {
        SQLException sqlex = new SQLException(errMessage);
        ConstraintViolationException cvex = new ConstraintViolationException("Constraint Violation", sqlex, "SOME_CONSTRAINT_NAME");
        return createDataIntegrityViolationException(cvex);
    }

    private DataIntegrityViolationException createDataIntegrityViolationException(Throwable rootCause) {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Data Integrity Violation", rootCause);
        return ex;
    }
}
