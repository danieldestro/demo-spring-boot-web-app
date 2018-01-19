package demo.app.core.data;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import demo.app.core.data.RecordNotFoundException;

public class RecordNotFoundExceptionTest {

    private static final String MSG_PREFIX = "Record not found: ";

    @Test
    public void test_arg_null() {
        RecordNotFoundException ex = new RecordNotFoundException(null);
        assertEquals(ex.getMessage(), MSG_PREFIX + "null");
    }

    @Test
    public void test_arg_string() {
        RecordNotFoundException ex = new RecordNotFoundException("test");
        assertEquals(ex.getMessage(), MSG_PREFIX + "test");
    }

    @Test
    public void test_arg_integer() {
        RecordNotFoundException ex = new RecordNotFoundException(new Integer(100));
        assertEquals(ex.getMessage(), MSG_PREFIX + "100");
    }

    @Test
    public void test_arg_object() {
        Object obj = new Object();
        RecordNotFoundException ex = new RecordNotFoundException(obj);
        assertEquals(ex.getMessage(), MSG_PREFIX + obj.toString());
    }
}
