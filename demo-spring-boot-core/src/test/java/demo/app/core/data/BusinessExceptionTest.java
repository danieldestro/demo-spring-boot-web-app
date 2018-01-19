package demo.app.core.data;

import static org.junit.Assert.assertNull;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import demo.app.core.data.BusinessException;

public class BusinessExceptionTest {

    private static final String ERR_MSG = "a business exception";

    @Test
    public void test_arg_null() {
        BusinessException ex = new BusinessException(null);
        assertNull(ex.getMessage());
    }

    @Test
    public void test_arg_string() {
        BusinessException ex = new BusinessException(ERR_MSG);
        assertEquals(ex.getMessage(), ERR_MSG);
    }
}
