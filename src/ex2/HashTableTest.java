package ex2;

import ex1.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNull;

class HashTableTest {

    @ParameterizedTest
    @CsvSource({"3", "6", "16"})
    void count(int n) {

        HashTable hashTable = new HashTable();

        for (int i=0; i < n; i++){
            hashTable.put("key"+i, "value"+i);
        }
        hashTable.drop("key0");
        hashTable.put("key1", "prueba");
        System.out.println(hashTable.toString());
        Assertions.assertEquals(n-1, hashTable.count());
    }

    @Test
    void size() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(16, hashTable.size());
    }

    @ParameterizedTest
    @CsvSource({"key1, value1", "key, value2", "10, value3"})
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
        //fuerzo una colision y borro el primer elemento
        if(key.equals("10")){
            hashTable.put(key, "collisioneeees" );
            hashTable.put("key", "value");
            System.out.println(hashTable.toString());
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            Assertions.assertNotEquals("", hashTable.toString());
        //borrar elemento existente y otro que no
        }else if(key.equals("key") || key.equals("key1")){
            hashTable.put("key", "value");
            //System.out.println(hashTable.getCollisionsForKey("key", 4));
            hashTable.drop(key);
            assertNull(hashTable.get(key));
        //3 elementos en colision, borrar el de enmedio
        }else {
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

//ERRORES:
// 1 - Al añadir un elemento con una key existente, el elemento no se sustituye
// 2 - Al borrar el primer elemento de una colisión, se borra toda la linea
// 3 - Sumar items cuando se añade un elemento
// 4 - Restar items cuando se borra un elemento