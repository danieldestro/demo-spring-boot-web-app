package demo.app.web.vo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.Objects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demo.app.web.vo.BaseVO;

public class BaseVOTest<T> {

    private static final Integer ID = 10;

    private BaseVO<Integer>      vo;

    private TestBaseVO createNew() {
        TestBaseVO newVo = new TestBaseVO();
        newVo.setId(ID);
        return newVo;
    }

    @BeforeMethod
    public void init() {
        vo = createNew();
    }

    @Test
    public void test_id() {
        assertEquals(vo.getId(), ID);
    }

    @Test
    public void test_equals() {
        BaseVO<Integer> otherVo = createNew();

        assertEquals(vo, otherVo);

        otherVo.setId(111);
        assertNotEquals(vo, otherVo);

        assertNotEquals(vo, "different");
    }

    @Test
    public void test_hashCode() {

        assertEquals(vo.hashCode(), Objects.hash(ID));
    }

    @Test
    public void test_toString() {

        final String className = TestBaseVO.class.getName();

        String str = vo.toString();
        assertEquals(str.substring(0, str.indexOf('@')), className);
        assertEquals(str.substring(str.indexOf('[')), "[id=10]");
    }

    /**
     * Dummy implementation of BaseVO for testing purpose.
     */
    class TestBaseVO extends BaseVO<Integer> {
        private static final long serialVersionUID = 7587666377330746161L;
    };
}
