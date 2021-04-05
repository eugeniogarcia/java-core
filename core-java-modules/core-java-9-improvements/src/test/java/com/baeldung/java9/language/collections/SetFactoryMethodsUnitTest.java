package com.baeldung.java9.language.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetFactoryMethodsUnitTest {

    @Test
    public void whenSetCreated_thenSuccess() {
        Set<String> traditionlSet = new HashSet<String>();
        traditionlSet.add("foo");
        traditionlSet.add("bar");
        traditionlSet.add("baz");
        Set<String> factoryCreatedSet = Set.of("foo", "bar", "baz");
        assertEquals(traditionlSet, factoryCreatedSet);
    }

    @Test
    public void onDuplicateElem_IfIllegalArgExp_thenSuccess() {
   	 Assertions.assertThrows(IllegalArgumentException.class, () -> {
         Set.of("foo", "bar", "baz", "foo");
		  });
    }

    @Test
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
      	 Assertions.assertThrows(UnsupportedOperationException.class, () -> {
             Set<String> set = Set.of("foo", "bar");
             set.add("baz");
    		  });
    }

    @Test
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
     	 Assertions.assertThrows(UnsupportedOperationException.class, () -> {
             Set<String> set = Set.of("foo", "bar", "baz");
             set.remove("foo");
    		  });
    }

    @Test
    public void onNullElem_ifNullPtrExpnThrown_thenSuccess() {
    	 Assertions.assertThrows(NullPointerException.class, () -> {
    	        Set.of("foo", "bar", null);
    		  });
    }

    @Test
    public void ifNotHashSet_thenSuccess() {
        Set<String> list = Set.of("foo", "bar");
        assertFalse(list instanceof HashSet);
    }

    @Test
    public void ifSetSizeIsOne_thenSuccess() {
        int[] arr = { 1, 2, 3, 4 };
        Set<int[]> set = Set.of(arr);
        assertEquals(1, set.size());
        assertArrayEquals(arr, set.iterator().next());
    }

}
