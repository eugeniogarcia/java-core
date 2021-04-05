package com.baeldung.java9.language.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapFactoryMethodsUnitTest {

    @Test
    public void whenMapCreated_thenSuccess() {
        Map<String, String> traditionlMap = new HashMap<String, String>();
        traditionlMap.put("foo", "a");
        traditionlMap.put("bar", "b");
        traditionlMap.put("baz", "c");
        Map<String, String> factoryCreatedMap = Map.of("foo", "a", "bar", "b", "baz", "c");
        assertEquals(traditionlMap, factoryCreatedMap);
    }

    @Test
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
     	 Assertions.assertThrows(UnsupportedOperationException.class, () -> {
             Map<String, String> map = Map.of("foo", "a", "bar", "b");
             map.put("baz", "c");

     	 });
    }

    @Test
    public void onElemModify_ifUnSupportedOpExpnThrown_thenSuccess() {
     	 Assertions.assertThrows(UnsupportedOperationException.class, () -> {
             Map<String, String> map = Map.of("foo", "a", "bar", "b");
             map.put("foo", "c");

     	 });
    }

    @Test
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
     	 Assertions.assertThrows(UnsupportedOperationException.class, () -> {
             Map<String, String> map = Map.of("foo", "a", "bar", "b");
             map.remove("foo");

     	 });
    }

    @Test
    public void givenDuplicateKeys_ifIllegalArgExp_thenSuccess() {
     	 Assertions.assertThrows(IllegalArgumentException.class, () -> {
             Map.of("foo", "a", "foo", "b");
     	 });
    }

    @Test
    public void onNullKey_ifNullPtrExp_thenSuccess() {
     	 Assertions.assertThrows(NullPointerException.class, () -> {
             Map.of("foo", "a", null, "b");
     	 });
    }

    @Test
    public void onNullValue_ifNullPtrExp_thenSuccess() {
     	 Assertions.assertThrows(NullPointerException.class, () -> {
             Map.of("foo", "a", "bar", null);
     	 });
    }

    @Test
    public void ifNotHashMap_thenSuccess() {
        Map<String, String> map = Map.of("foo", "a", "bar", "b");
        assertFalse(map instanceof HashMap);
    }

}
