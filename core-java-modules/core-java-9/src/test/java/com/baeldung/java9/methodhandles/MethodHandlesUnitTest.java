package com.baeldung.java9.methodhandles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Test case for the {@link MethodHandles} API
 */
public class MethodHandlesUnitTest {

    @Test
    public void givenConcatMethodHandle_whenInvoked_thenCorrectlyConcatenated() throws Throwable {

        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(String.class, String.class);
        final MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        final String output = (String) concatMH.invoke("Effective ", "Java");

        assertEquals("Effective Java", output);
    }

    @Test
    public void givenAsListMethodHandle_whenInvokingWithArguments_thenCorrectlyInvoked() throws Throwable {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(List.class, Object[].class);
        final MethodHandle asListMH = publicLookup.findStatic(Arrays.class, "asList", mt);

        final List<Integer> list = (List<Integer>) asListMH.invokeWithArguments(1, 2);

        assertIterableEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void givenConstructorMethodHandle_whenInvoked_thenObjectCreatedCorrectly() throws Throwable {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(void.class, String.class);
        final MethodHandle newIntegerMH = publicLookup.findConstructor(Integer.class, mt);

        final Integer integer = (Integer) newIntegerMH.invoke("1");

        assertEquals(1, integer.intValue());
    }

    @Test
    public void givenAFieldWithoutGetter_whenCreatingAGetter_thenCorrectlyInvoked() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle getTitleMH = lookup.findGetter(Book.class, "title", String.class);

        final Book book = new Book("ISBN-1234", "Effective Java");

        assertEquals("Effective Java", getTitleMH.invoke(book));
    }

    @Test
    public void givenPrivateMethod_whenCreatingItsMethodHandle_thenCorrectlyInvoked() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final Method formatBookMethod = Book.class.getDeclaredMethod("formatBook");
        formatBookMethod.setAccessible(true);

        final MethodHandle formatBookMH = lookup.unreflect(formatBookMethod);

        final Book book = new Book("ISBN-123", "Java in Action");

        assertEquals("ISBN-123 > Java in Action", formatBookMH.invoke(book));
    }

    @Test
    public void givenReplaceMethod_whenUsingReflectionAndInvoked_thenCorrectlyReplaced() throws Throwable {
        final Method replaceMethod = String.class.getMethod("replace", char.class, char.class);

        final String string = (String) replaceMethod.invoke("jovo", 'o', 'a');

        assertEquals("java", string);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvoked_thenCorrectlyReplaced() throws Throwable {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        final MethodHandle replaceMH = publicLookup.findVirtual(String.class, "replace", mt);

        final String replacedString = (String) replaceMH.invoke("jovo", Character.valueOf('o'), 'a');

        assertEquals("java", replacedString);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvokingExact_thenCorrectlyReplaced() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        final MethodHandle replaceMH = lookup.findVirtual(String.class, "replace", mt);

        final String s = (String) replaceMH.invokeExact("jovo", 'o', 'a');

        assertEquals("java", s);
    }

    @Test
    public void givenSumMethodHandle_whenInvokingExact_thenSumIsCorrect() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        final MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        final int sum = (int) sumMH.invokeExact(1, 11);

        assertEquals(12, sum);
    }

    @Test
    public void givenSumMethodHandleAndIncompatibleArguments_whenInvokingExact_thenException() throws Throwable {
      	 Assertions.assertThrows(WrongMethodTypeException.class, () -> {
             final MethodHandles.Lookup lookup = MethodHandles.lookup();
             final MethodType mt = MethodType.methodType(int.class, int.class, int.class);
             final MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

             sumMH.invokeExact(Integer.valueOf(1), 11);
    		  });
    }

    @Test
    public void givenSpreadedEqualsMethodHandle_whenInvokedOnArray_thenCorrectlyEvaluated() throws Throwable {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(boolean.class, Object.class);
        final MethodHandle equalsMH = publicLookup.findVirtual(String.class, "equals", mt);

        final MethodHandle methodHandle = equalsMH.asSpreader(Object[].class, 2);

        assertTrue((boolean) methodHandle.invoke(new Object[] { "java", "java" }));
        assertFalse((boolean) methodHandle.invoke(new Object[] { "java", "jova" }));
    }

    @Test
    public void givenConcatMethodHandle_whenBindToAString_thenCorrectlyConcatenated() throws Throwable {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        final MethodType mt = MethodType.methodType(String.class, String.class);
        final MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        final MethodHandle bindedConcatMH = concatMH.bindTo("Hello ");

        assertEquals("Hello World!", bindedConcatMH.invoke("World!"));
    }
}
