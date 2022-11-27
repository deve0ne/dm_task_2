package com.deveone.dm_task_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int comparisons = 0;

    public static void main(String[] args) {
        int[] numbers = readArrFromFile("input.txt");

        if (numbers.length == 0) {
            System.err.println("Number array is empty");
            return;
        }

        quickSort(numbers);

        writeAnswerToFile("output.txt", numbers, comparisons);
    }

    private static void quickSort(int[] numbers) {
        comparisons = 0;
        quickSort(numbers, 0, numbers.length - 1);
    }

    /* Тут считаются не все сравнения, проводимые сортировкой.
    Как я понимаю, должны считаться именно прямые сравнения чисел массива,
    поэтмоу только их и считаем, а сравнения индексов игнорируем.
    Но на всякий случай оставил закомментированные счётчики остальных сравнений. */
    private static void quickSort(int[] numbers, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = numbers[(leftBorder + rightBorder) / 2];

        do {
            while (numbers[leftMarker] < pivot) {
                leftMarker++;
                comparisons++;
            }

            while (numbers[rightMarker] > pivot) {
                rightMarker--;
                comparisons++;
            }

//            comparisons++;
            if (leftMarker <= rightMarker) {
//                comparisons++;
                if (leftMarker < rightMarker) {
                    int tmp = numbers[leftMarker];
                    numbers[leftMarker] = numbers[rightMarker];
                    numbers[rightMarker] = tmp;
                }

                leftMarker++;
                rightMarker--;
            }

//            comparisons++;
        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder) {
//            comparisons++;
            quickSort(numbers, leftMarker, rightBorder);
        }

        if (leftBorder < rightMarker) {
//            comparisons++;
            quickSort(numbers, leftBorder, rightMarker);
        }
    }

    private static int[] readArrFromFile(String filepath) {
        File file = new File(filepath);
        List<Integer> numbers = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);

            while (scanner.hasNext()) {
                if (scanner.hasNextInt())
                    numbers.add(scanner.nextInt());
                else
                    scanner.next();
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error on reading from file :(");
            e.printStackTrace();
            return new int[0];
        }

        return numbers.stream().mapToInt(i -> i).toArray();
    }

    private static void writeAnswerToFile(String filepath, int[] sortedNumbers, int comparisons) {
        File file = new File(filepath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Error on creating file :(");
            e.printStackTrace();
            return;
        }

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);

            StringBuilder text = new StringBuilder();

            for (int num : sortedNumbers)
                text.append(num).append(" ");

            text.append('\n').append(comparisons);

            writer.write(text.toString());

            writer.close();
        } catch (IOException e) {
            System.err.println("Error on writing to file :(");
            e.printStackTrace();
        }
    }
}