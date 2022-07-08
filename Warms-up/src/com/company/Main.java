package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println ("Please enter a string: ");
        String line = scanner.nextLine(); // Вводим строку
        String reverseString = reverseString (line); // Переворачиваем
        System.out.println ("Your string: " + line);
        System.out.println ("Your reverse string: " + reverseString);

        // Выполнение методов замерки времени работы
        Time (line, 1000);

        Time (line, 10000);

        Time (line, 100000);

    }

    private static void Time(String line, int n) { // Метод для замерки времени работы для N повторений
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            reverseString(line);
        }

        // Подсчет времени работы и вывод на экран
        System.out.printf ("%d milliseconds%n", System.currentTimeMillis() - startTime);
    }

    private static String reverseString(String line) { // Метод для переворта строки
        if(line.length () <= 1) return line; // Если строка состоит из одного символа возвращаем ее

        line = new StringBuilder (line).reverse ().toString ();
        return line;
    }
}
