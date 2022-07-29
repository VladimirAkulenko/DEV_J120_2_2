/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_2_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author USER
 */
public class ReaderWriter {
    private File file;
    static Map<String, Integer> dictionaryList = new HashMap<>();

    public ReaderWriter(File file) {
        if (file == null) throw new IllegalArgumentException("Файл не может быть пустым");
        this.file = file;
    }

    // чтение файла
    public Map<String, Integer> read () throws IOException{

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String s;
            while ((s = br.readLine()) != null) {
                if (s.isEmpty()) continue;
                StringBuilder word = new StringBuilder();
                char[] charArray = s.toCharArray();
                char curChar;
                for (int i = 0; i < s.length(); i++) {
                    curChar = charArray[i];
                    if (Character.UnicodeBlock.of(charArray[i]).equals(Character.UnicodeBlock.CYRILLIC) || curChar == ('-')) {
                        word.append(curChar);
                        if (i!=s.length()-1) continue;
                    }
                    if (!word.isEmpty()) {
                        if (dictionaryList.containsKey(word.toString().toLowerCase(Locale.ROOT))) {
                            int savVal = dictionaryList.get(word.toString().toLowerCase(Locale.ROOT));
                            dictionaryList.put(word.toString().toLowerCase(Locale.ROOT), savVal+1);
                            word.delete(0, word.length());
                            continue;
                        } else {
                            dictionaryList.put(word.toString().toLowerCase(), 1);
                            word.delete(0, word.length());
                        }
                    }
                }
            }
        }
        return dictionaryList;
    }

    // запись результата в два файла отчета
    public static void saveReport () throws FileNotFoundException {
        final Map<String, Integer> sorted = sortedMap(dictionaryList);
        PrintWriter printWriter = new PrintWriter(Main.REPORT1);
        sorted.forEach((k, v) -> {
            printWriter.println(k + " relative = " + v + "  absolute = " + (double) v / sorted.size());
        });

        PrintWriter pw2 = new PrintWriter(Main.REPORT2);
        // создадим компаратор, который будет сравнивать значения, а не ключи
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String k1, String k2) {
                int compare = dictionaryList.get(k2).compareTo(dictionaryList.get(k1));
                if (compare == 0)
                    return k1.compareTo(k2);
                return compare;
            }
        };
        Map<String, Integer> sortedByValues = new TreeMap<>(comparator);
        sortedByValues.putAll(sorted);


        sortedByValues.forEach((k, v) -> {
            pw2.println(k + " relative=" + v + "  absolute=" + (double) v / sorted.size());
        });
    }

    private static Map<String, Integer> sortedMap(Map<String, Integer> list) {
        Map<String, Integer> sorted = new TreeMap<>();
        sorted.putAll(list);
        return sorted;
    }
}
