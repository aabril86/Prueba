package ex1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void count() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(0, hashTable.count());

    }

    @org.junit.jupiter.api.Test
    void size() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(16, hashTable.size());
    }

    @ParameterizedTest
    @CsvSource({"key1, value1", "key, value2"})
    //probar elemento que no colisione primero, otro con una key ya existente, y otro que colisione HAY QUE UTILITZAR EL METODO getCollisionsForKey
    void put(String key, String value) {
        HashTable hashTable = new HashTable();
        hashTable.put("key","value");
        System.out.println(hashTable.getCollisionsForKey("key", 5));
        hashTable.put(key, value);
        Assertions.assertEquals(value, hashTable.get(key));
    }

    @org.junit.jupiter.api.Test
    void get() {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        Assertions.assertEquals("value", hashTable.get("key"));
    }

    @Test
    void drop() {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        hashTable.drop("key");
        assertNull(hashTable.get("key"));

    }
}