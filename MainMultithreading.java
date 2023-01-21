package working_with_threads.home_work01_interval_of_values;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Leonid Zulin
 * @date 14.01.2023 16:37
 */
public class MainMultithreading {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        List<Thread> threads = new ArrayList<>();// for storage of threads

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {
            Thread thread;
            thread = new Thread(() -> {
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i; // update interval
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            threads.add(thread);
            thread.start();
        }


        for (Thread thread : threads) {
            thread.join();
        }
        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + " ms");

        // return to thread main
        System.out.println("Main ends!!!");
    }

    private static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();

    }

}
