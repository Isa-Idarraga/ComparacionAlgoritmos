package practicaSort;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class bucketSort {

    public static List<String> readWordsFromFile(String filePath, int limit) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < limit) {
                words.add(line);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void bucketSort(String[] arr, int bucketCount) {
        // Crear las cubetas
        List<List<String>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Encontrar los límites de las cubetas según el rango de valores lexicográficos
        String minValue = arr[0];
        String maxValue = arr[0];
        for (String s : arr) {
            if (s.compareTo(minValue) < 0) {
                minValue = s;
            }
            if (s.compareTo(maxValue) > 0) {
                maxValue = s;
            }
        }

        // Distribuir las palabras en las cubetas
        for (String s : arr) {
            int bucketIndex = getBucketIndex(s, minValue, maxValue, bucketCount);
            buckets.get(bucketIndex).add(s);
        }

        // Ordenar cada cubeta usando una función personalizada y luego combinar los resultados
        int index = 0;
        for (List<String> bucket : buckets) {
            bucket.sort(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    // Comparar directamente las versiones originales
                    return s1.compareTo(s2);
                }
            });
            for (String s : bucket) {
                arr[index++] = s;
            }
        }
    }

    public static int getBucketIndex(String s, String minValue, String maxValue, int bucketCount) {
        // Calcula el rango, asegurando que no sea 0
        int range = maxValue.compareTo(minValue);
        if (range == 0) return 0;  // Todos los elementos son iguales

        // Evitar división por cero, ajustamos bucketRange
        double bucketRange = (double) range / bucketCount;
        if (bucketRange == 0) {
            bucketRange = 1;  // Si el bucketRange es muy pequeño, ajustarlo a 1
        }

        int bucketIndex = (int) ((s.compareTo(minValue)) / bucketRange);
        return Math.min(bucketIndex, bucketCount - 1); // Garantizar que el índice no supere el número de cubetas
    }

    public static void main(String[] args) {
        // Especifica la ruta al archivo .txt
        String filePath = "/workspaces/ComparacionAlgoritmos/palabras.txt";
        int numberOfWords = 247047;
        int bucketCount = 26;  // Puedes ajustar el número de cubetas

        // Leer las numberOfWords
        List<String> wordList = readWordsFromFile(filePath, numberOfWords);

        // Convertir la lista a un arreglo para el sorting
        String[] words = wordList.toArray(new String[0]);

        // Medir el tiempo antes de la ordenación
        long startTime = System.nanoTime();

        // Aplicar Bucket Sort
        bucketSort(words, bucketCount);

        // Medir el tiempo después de la ordenación
        long endTime = System.nanoTime();

        // Calcular el tiempo de ejecución
        long duration = endTime - startTime;

        // Imprimir solo el tiempo de ejecución
        System.out.println("Tiempo de ejecución en nanosegundos: " + duration);
        System.out.println("Tiempo de ejecución en milisegundos: " + duration / 1_000_000.0);
    }
}
