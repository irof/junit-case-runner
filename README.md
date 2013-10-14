# これはなに

JUnitのアノテーションでパラメタライズドをアレするソレです。

## sample

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
        @Case("someString")
        public void simpleTestCase(String param) {
            assertThat(param, is("someString"));
        }

        @Tests({
                @Case({"str1", "str1"}),
                @Case({"str2", "str2"}),
                @Case({"str3", "str3"})
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
    }

## 使いかたとかのメモ

* RunWith に CaseRunner を指定する。
* @Test に続けて @Case で入れたいパラメータを入れる。
* 複数指定したい場合（こっちがメイン）は @Tests を使う。
* パラメータは全て String で書く。
* 書いたパラメータはメソッド引数になる。とりあえずStringかプリミティブ。
* Case に書いた個数とメソッド引数の個数が合ってなきゃコケる。
* 別途 Parser を指定すれば好きな型が使える。けど未だ外から使えるようになってない。
* Tests で Case を使用した場合のテストレポートのメソッド名は `メソッド名_[パラメータ]` になる。
