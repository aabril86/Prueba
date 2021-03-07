package ex1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @ParameterizedTest
    @CsvSource({"3", "6", "16"})
    void count(int n) {

        HashTable hashTable = new HashTable();

        for (int i=0; i < n; i++){
            hashTable.put("key"+i, "value"+i);
        }
        hashTable.drop("key0");
        System.out.println(hashTable.toString());
        Assertions.assertEquals(n-1, hashTable.count());
    }

    @org.junit.jupiter.api.Test
    void size() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(16, hashTable.size());
    }

    @ParameterizedTest
    @CsvSource({"key1, value1", "key, value2", "10, value3", })
    //probar elemento que no colisione primero, otro con una key ya existente, y otro que colisione HAY QUE UTILITZAR EL METODO getCollisionsForKey
    void put(String key, String value) {
        HashTable hashTable = new HashTable();
        hashTable.put("key","value");
        System.out.println(hashTable.getCollisionsForKey("key", 5));
        hashTable.put(key, value);
        System.out.println(hashTable.toString());
        Assertions.assertEquals(value, hashTable.get(key));
    }

    @ParameterizedTest
    @CsvSource({ "key, value","key1, value1"})
    void get(String key, String value) {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        if(key.equals("key")) Assertions.assertEquals(value, hashTable.get(key));
        else Assertions.assertNull(hashTable.get(key));
    }

    @ParameterizedTest
    @CsvSource({"key", "key1", "10", "21"})
    void drop(String key) {
        HashTable hashTable = new HashTable();
        if(key.equals("10")){
            hashTable.put(key, "collisioneeees" );
            hashTable.put("key", "value");
            System.out.println(hashTable.toString());
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            Assertions.assertNotEquals("", hashTable.toString());
        }else if(key.equals("key") || key.equals("key1")){
            hashTable.put("key", "value");
            //System.out.println(hashTable.getCollisionsForKey("key", 4));
            hashTable.drop(key);
            assertNull(hashTable.get(key));
        }else{
            hashTable.put("key", "value");
            hashTable.put(key, "collisioneeees" );
            hashTable.put("10", "collisioneeees1" );
            System.out.println(hashTable.count());
            System.out.println(hashTable.toString());
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            Assertions.assertNotEquals("", hashTable.toString());
        }
    }
}

//
// ERRORES: COUNT NO VA, NO SUMABA AL HACER PUT NI RESTABA AL HACER DROP |  DROP DELETEA TODA LA LINEA DE COLISIONES SI SE BORRA LA PRIMERA | PUT NO SUSTITUYE CUANDO METES UNA KEY EXISTENTE