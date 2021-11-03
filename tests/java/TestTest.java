package io.github.humbleui.skija.test;

import static io.github.humbleui.skija.test.runner.TestRunner.assertArrayEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertNotEquals;
import static io.github.humbleui.skija.test.runner.TestRunner.assertThrows;
import static io.github.humbleui.skija.test.runner.TestRunner.popStack;
import static io.github.humbleui.skija.test.runner.TestRunner.pushStack;

import java.util.NoSuchElementException;

import io.github.humbleui.skija.test.runner.*;

public class TestTest implements Executable {
    @Override
    public void execute() throws Exception {
        TestRunner.testMethod(this, "abc");
        throw new Exception("Unexpected in execute");
    }

    public void abc() throws Exception {
        assertEquals(true, true);
        assertEquals(true, false);

        assertNotEquals(null, false);
        assertNotEquals(true, true);

        assertArrayEquals(new Integer[] {1, 2, 3},
                          new Integer[] {1, 2, 3});
        assertArrayEquals(new Integer[] {1, 2, 3},
                          new Integer[] {1, 2});
        assertArrayEquals(new Integer[] {1, 2, 3},
                          new Integer[] {1, 2, 4});

        assertThrows(NoSuchElementException.class, ()-> { throw new NoSuchElementException(); });
        assertThrows(NoSuchElementException.class, ()-> { throw new Exception("abc"); });
        assertThrows(NoSuchElementException.class, ()-> { System.gc(); });

        for (int i = 0; i < 10; ++i) {
            pushStack("i = " + i);
            assertEquals(0, i % 2);
            popStack();
        }

        throw new Exception("Unexpected in method");
    }
}