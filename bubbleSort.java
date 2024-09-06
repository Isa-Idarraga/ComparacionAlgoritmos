package practicaSort;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class bubbleSort {

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

    public static void bubbleSort(String[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                //comparar las cadenas
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }


    public static void main(String[] args) {
        // Especifica la ruta completa al archivo .txt
        String filePath = "/workspaces/ComparacionAlgoritmos/palabras.txt";
        int numberOfWords = 247047;

        // Leer las numberOfWords
        List<String> wordList = readWordsFromFile(filePath, numberOfWords);

        // Convertir la lista a un arreglo para el sorting
        String[] words = wordList.toArray(new String[0]);


        // Medir el tiempo antes de la ordenación
        long startTime = System.nanoTime();

        // Aplicar Bubble Sort
        bubbleSort(words);

        // Medir el tiempo después de la ordenación
        long endTime = System.nanoTime();

        // Calcular el tiempo de ejecución
        long duration = endTime - startTime;
        // System.out.println("\nDespués de ordenar:");
        // for (String word : words) {
        //     System.out.println(word);
        // }

        System.out.println("\nTiempo de ejecución en nanosegundos: " + duration);
        System.out.println("Tiempo de ejecución en milisegundos: " + duration / 1_000_000.0);
    }
}