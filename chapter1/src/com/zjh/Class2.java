package com.zjh;

import java.math.BigDecimal;

/**
 * Class2 class
 *
 * @author zjh
 * @date 2022/7/12 9:29
 */
public class Class2 {
    public static void main(String[] args) {
        int a = 1000;
        int b = 1000;
        
        a = a^b;
        b = a^b;
        a = a^b;

        System.out.println(new BigDecimal("11").add(new BigDecimal("12")));
    }
}
