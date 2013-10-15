import net.hogedriven.junit.Case;
import net.hogedriven.junit.CaseRunner;
import net.hogedriven.junit.Tests;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(CaseRunner.class)
public class CaseRunnerTest {

    @Test
    public void normalTest() {
        String actual = "abc";
        String expected = "abc";
        assertThat(actual, is(expected));
    }

    @Test
    @Case("someString")
    public void simpleTestCase(String param) {
        assertThat(param, is("someString"));
    }

    @Tests({
            @Case({"ghk", "ghk"}),
            @Case({"lmn", "lmn"}),
            @Case({"op", "op"})
    })
    public void parameterTestsCase(String actual, String expected) {
        assertThat(actual, is(expected));
    }

    @Test
    @Case("123")
    public void intTest(int i) {
        assertThat(i, is(123));
    }

    @Test
    @Case({"true", "1234567890123", "12.3", "12.34", "1"})
    public void primitives(boolean bool, long l, float f, double d, byte b) {
        assertThat(bool, is(true));
        assertThat(l, is(1234567890123L));
        assertThat(f, is(12.3F));
        assertThat(d, is(12.34D));
        assertThat(b, is((byte) 1));
    }

    @Test
    @Case({"HOGE"})
    public void enumTest(Hoge h) {
        assertThat(h, is(Hoge.HOGE));
    }
}

enum Hoge {
    HOGE,FUGA
}