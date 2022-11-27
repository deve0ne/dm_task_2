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

            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    int tmp = numbers[leftMarker];
                    numbers[leftMarker] = numbers[rightMarker];
                    numbers[rightMarker] = tmp;
                }

                leftMarker++;
                rightMarker--;
            }

        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder)
            quickSort(numbers, leftMarker, rightBorder);

        if (leftBorder < rightMarker)
            quickSort(numbers, leftBorder, rightMarker);
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
            e.printStackTrace();
        }
    }
}