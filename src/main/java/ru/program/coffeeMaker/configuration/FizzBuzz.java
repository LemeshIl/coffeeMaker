package ru.program.coffeeMaker.configuration;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
    public static void main(String[] args) {

        // FizzBuzz.fizzBuzz();
        FizzBuzz.countWord("AAAABBBBBBB");
    }


    //fizzBuzz
    public static void fizzBuzz() {
        for (int i = 1; i < 101; i++) {
            if (i % 15 == 0) {
                System.out.println("fizzbuzz");
            } else {
                if (i % 5 == 0) {
                    System.out.println("fizz");
                } else {
                    if (i % 3 == 0) {
                        System.out.println("buzz");
                    } else {
                        System.out.println(i);
                    }
                }
            }
        }
    }

    //AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
    public static void countWord(String s) {
        List<Object> list = new ArrayList<>();
        int count = 1;
        char etalon;
        for (int i = 0; i < s.length() - 1; i++) {

           // etalon = s.charAt(i);
            if ((s.charAt(i) == s.charAt(i + 1))) {
                count++;
            } else {
                list.add(s.charAt(i));
                list.add(count);
              //  etalon = s.charAt(i + 1);
               count = 1;

            }
        }
        System.out.println(list);
    }
}

