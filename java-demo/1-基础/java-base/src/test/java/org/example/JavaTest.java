package org.example;

import org.junit.Test;

public class JavaTest {

    // == 和 equals()
    @Test
    public void test01(){
        Integer i1 = 11;
        Integer i2 = 11;
        Integer i3 = 11111;
        Integer i4 = 11111;
        System.out.println(i1 == i2);   // true
        System.out.println(i3 == i4);   // false
        Character c1 = '1';
        Character c2 = '1';
        System.out.println(c1 == c2);   // true
        // throw new RuntimeException(new Exception());
    }
}
