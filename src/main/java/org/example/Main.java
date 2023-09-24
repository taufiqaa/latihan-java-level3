package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String DATA_FILE_PATH = "C://temp/direktori/data_sekolah.csv";
    private static final String OUTPUT_DIRECTORY = "C://temp/direktori/";
    private static final String MODUS_FILE_NAME = "data_sekolah_modus.txt";
    private static final String MODUS_MEDIAN_FILE_NAME = "data_sekolah_modus_median.txt";

    private static Map<String, List<Integer>> readDataFromFile() {
        Map<String, List<Integer>> classData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String className = parts[0];

                List<Integer> values = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    try {
                        int value = Integer.parseInt(parts[i].trim());
                        values.add(value);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid integer value: " + parts[i]);
                    }
                }

                classData.put(className, values);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return classData;
    }

    private static void writeModusToFile(Map<String, List<Integer>> classData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_DIRECTORY + MODUS_FILE_NAME))) {
            writer.println("Berikut Hasil Pengolahan Nilai:");
            writer.println();
            writer.println("Nilai\t\t\t\t\t| Frekuensi");

            for (Map.Entry<String, List<Integer>> entry : classData.entrySet()) {
                String className = entry.getKey();
                writer.println("Kelas " + className);

                List<Integer> values = entry.getValue();
                for (Integer value : values) {
                    writer.println(value + "\t\t\t\t\t| 1");
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void writeModusMedianToFile(Map<String, List<Integer>> classData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_DIRECTORY + MODUS_MEDIAN_FILE_NAME))) {
            writer.println("Berikut Hasil Pengolahan Nilai:");
            writer.println();
            writer.println("Berikut hasil sebaran data nilai");

            List<Integer> allValues = new ArrayList<>();

            for (Map.Entry<String, List<Integer>> entry : classData.entrySet()) {
                List<Integer> values = entry.getValue();
                allValues.addAll(values);
            }

            double mean = calculateMean(allValues);
            double median = calculateMedian(allValues);
            int mode = calculateMode(allValues);

            writer.println("Mean: " + mean);
            writer.println("Median: " + median);
            writer.println("Modus: " + mode);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static double calculateMean(List<Integer> data) {
        if (data.isEmpty()) return 0;

        double sum = 0;
        for (Integer value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    private static double calculateMedian(List<Integer> data) {
        if (data.isEmpty()) return 0;

        data.sort(null);
        int middle = data.size() / 2;
        if (data.size() % 2 == 0) {
            return (data.get(middle - 1) + data.get(middle)) / 2.0;
        } else {
            return data.get(middle);
        }
    }

    private static int calculateMode(List<Integer> data) {
        if (data.isEmpty()) return 0;

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        int maxFrequency = 0;
        int mode = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }

    private static void generateModusFile(Map<String, List<Integer>> classData) {
        writeModusToFile(classData);
    }

    private static void generateModusMedianFile(Map<String, List<Integer>> classData) {
        writeModusMedianToFile(classData);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Map<String, List<Integer>> classData = readDataFromFile();

            while (true) {
                System.out.println("------------------------------------------------------------------------------------------------------------");
                System.out.println("Aplikasi Pengolah Nilai Siswa");
                System.out.println("------------------------------------------------------------------------------------------------------------");
                System.out.println("Letakkan file csv dengan nama file data_kampus di direktori berikut : " + DATA_FILE_PATH);
                System.out.println("Pilih menu:");
                System.out.println("1. Generate txt untuk menampilkan modus");
                System.out.println("2. Generate txt untuk menampilkan nilai rata-rata, median");
                System.out.println("3. Generate kedua file");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        generateModusFile(classData);
                        System.out.println("------------------------------------------------------------------------------------------------------------");
                        System.out.println("Aplikasi Pengolah Nilai Siswa");
                        System.out.println("------------------------------------------------------------------------------------------------------------");
                        System.out.println("File telah digenerate di " + OUTPUT_DIRECTORY + MODUS_FILE_NAME);
                        System.out.println("Silahkan cek");
                        System.out.println("0. Exit");
                        System.out.println("1. Kembali ke Menu Utama");
                        break;
                    case 2:
                        generateModusMedianFile(classData);
                        System.out.println("------------------------------------------------------------------------------------------------------------");
                        System.out.println("Aplikasi Pengolah Nilai Siswa");
                        System.out.println("------------------------------------------------------------------------------------------------------------");
                        System.out.println("File telah digenerate di " + OUTPUT_DIRECTORY + MODUS_MEDIAN_FILE_NAME);
                        System.out.println("Silahkan cek");
                        System.out.println("0. Exit");
                        System.out.println("1. Kembali ke Menu Utama");
                        break;
                    case 3:
                        generateModusFile(classData);
                        generateModusMedianFile(classData);
                        System.out.println("File telah digenerate di " + OUTPUT_DIRECTORY);
                        System.out.println("Silahkan cek");
                        System.out.println("0. Exit");
                        System.out.println("1. Kembali ke Menu Utama");
                        break;
                    case 0:
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                }

                int returnChoice = scanner.nextInt();
                if (returnChoice == 1) {
                    continue;
                } else if (returnChoice == 0) {
                    scanner.close();
                    System.exit(0);
                } else {
                    System.out.println("Pilihan tidak valid. Aplikasi akan keluar.");
                    scanner.close();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
