/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dev_j120_2_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class Main {
    public static final String REPORT1 = "report1.txt";
    public static final String REPORT2 = "report2.txt";


    public static void main(String[] args) {
        System.out.println("Введите URL текстового файла для анализа в следущем формате DiscName:\\folders\\fileName.txt:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        ReaderWriter readerWriter = new ReaderWriter(new File(url));

            try {
                readerWriter.read();
            } catch (IOException e) {
                System.out.println("Файл \"" + url + "\" не существует или не может быть прочитан.");
                System.exit(1);
            }

        try {
            ReaderWriter.saveReport();
        } catch (FileNotFoundException e) {
            System.out.println("Какая-то проблема с записью в файл " + REPORT1 + " или " + REPORT2);
            System.exit(1);
        }
    }

}

