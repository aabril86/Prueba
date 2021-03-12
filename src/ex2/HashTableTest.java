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

    @Test
    //probar elemento que no colisione primero, otro con una key ya existente, y otro que colisione HAY QUE UTILITZAR EL METODO getCollisionsForKey
    void put() {
        //crear hashTable
        HashTable hashTable = new HashTable();
        //comprobar que esta vacía
        Assertions.assertEquals("", hashTable.toString());
        hashTable.put("key","value");
        //comprobar que se ha insertado el elemento
        Assertions.assertEquals("value", hashTable.get("key"));
        //Probar a insertar otro elemento usando la misma key (debe sustituirse)
        hashTable.put("key", "value2");
        Assertions.assertEquals("\n" +
                " bucket[15] = [key, value2]", hashTable.toString());

        //Insertar elemento que colisione (deberían salir los dos)
        hashTable.put(hashTable.getCollisionsForKey("key"), "value3");
        Assertions.assertEquals("\n" +
                " bucket[15] = [key, value2] -> [" + hashTable.getCollisionsForKey("key") + ", value3]", hashTable.toString());
    }

    @ParameterizedTest
    @CsvSource({ "key, value","key1, value1", "10, value2"})
    void get(String key, String value) {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        if(key.equals("key") || key.equals("key1")) {
            if (key.equals("key")) Assertions.assertEquals(value, hashTable.get(key));
            else Assertions.assertNull(hashTable.get(key));
        }else{
            hashTable.put(key, value);
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            assertNull(hashTable.get(key));
        }
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
            System.out.println(hashTable.toString());
            //borrar el 2o elemento de la colision
            hashTable.drop(key);
            Assertions.assertEquals("\n" +
                    " bucket[15] = [key, value] -> [10, collisioneeees1]" , hashTable.toString());

            //añadir elemento para volver a tener tres
            hashTable.put("32", "prueba3");
            //borrar el primer elemento
            hashTable.drop("key");
            Assertions.assertEquals("\n" +
                    " bucket[15] = [10, collisioneeees1] -> [32, prueba3]", hashTable.toString());
            //añadir elemento para tener 3 y borrar el ultimo
            hashTable.put("21", "prueba4");
            hashTable.drop("21");
            Assertions.assertEquals("\n" +
                    " bucket[15] = [10, collisioneeees1] -> [32, prueba3]", hashTable.toString());

            //borrar el primer y segundo elemento
            hashTable.put("21", "prueba5");
            hashTable.drop("10");
            hashTable.drop("32");
            Assertions.assertEquals("\n" +
                    " bucket[15] = [21, prueba5]", hashTable.toString());

            //primero y tercero
            hashTable.put("10", "prueba6");
            hashTable.put("32", "prueba7");
            hashTable.drop("21");
            hashTable.drop("32");
            System.out.println(hashTable.toString());
            Assertions.assertEquals("\n" +
                    " bucket[15] = [10, prueba6]", hashTable.toString());
        }
    }
}

//ERRORES:
// 1 - Al añadir un elemento con una key existente, el elemento no se sustituye DONE
// 2 - Al borrar el primer elemento de una colisión, se borra toda la linea DONE
// 3 - Al intentar borrar el primer elemento de una colision de más de dos, no se borra, Si intentamos borrar el primer elemento y el tercero, tampoco funciona DONE
// 4 - Sumar items cuando se añade un elemento DONE
// 5 - Restar items cuando se borra un elemento DONE
// 6 - Al colisionar un elemento, borrarlo e intentar hacer un get, obtenemos un Nullpointer (debería retornar null)
