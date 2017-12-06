package towerdefence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is a program (file) for *all* tests (testing the model)
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("towerdefence.Test Start");
        runTests();
        System.out.println("towerdefence.Test Finished");
    }

    private static void runTests() {
        Test test = new Test();
        Method[] methods = Test.class.getMethods();
        for (Method method : methods) {
            if (method.getName().contains("test")) {
                try {
                    method.invoke(test);
                    System.out.printf("Ok\t\t%s\n", method.getName());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.printf("Failed\t\t%s - %s\n", method.getName(), e.getCause());
                }
            }
        }
    }

    // Write your test methods here, see PigWTest for example
    // towerdefence.Test method should end with call to some Helper (below)
    // i.e. test should pass or exception

    // ------- Helpers -------------------

    private static void assertNotEquals(Number n1, Number n2) {
        if (n1.equals(n2)) {
            throw new IllegalStateException(n1 + "==" + n2);
        }
    }

    private static void assertEquals(Number n1, Number n2) {
        if (!n1.equals(n2)) {
            throw new IllegalStateException(n1 + "!=" + n2);
        }
    }

    private static void assertEquals(Object o1, Object o2) {
        if (!o1.equals(o2)) {
            throw new IllegalStateException(o1 + "!=" + o2);
        }
    }

    private static void assertEquals(boolean b1, boolean b2) {
        if (b1 != b2) {
            throw new IllegalStateException(b1 + "!=" + b2);
        }
    }

    private static void assertNotEquals(boolean b1, boolean b2) {
        if (b1 == b2) {
            throw new IllegalStateException(b1 + "==" + b2);
        }
    }

}
