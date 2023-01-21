package working_with_threads.home_work01_interval_of_values;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Leonid Zulin
 * @date 14.01.2023 16:37
 */
public class MainMultithreading {
    private final static int strCount = 25; // number of lines of text and threads
    private static int maxInterval = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[strCount];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        // Creating a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(strCount);
        List<Future<Integer>> allResultMaxSize = new ArrayList<>();// for storage of future results
        // In the loop, we send tasks to the thread pool for execution and get the answer
        for (String text : texts) {
            PartialProcessing task = new PartialProcessing(text);
            Future<Integer> futurePartProcessing = executorService.submit(task);
            // add in the our ArrayList
            allResultMaxSize.add(futurePartProcessing);
        }
        // The loop for waiting, receiving and processing the result
        for (Future<Integer> resultMaxSize : allResultMaxSize) {
            if (maxInterval < resultMaxSize.get()) {
                maxInterval = resultMaxSize.get();
            }
        }
        executorService.shutdown();
        System.out.println("\nМаксимальный интервал значений среди всех строк = " + maxInterval);

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

// Create a new class for Callable task
class PartialProcessing implements Callable<Integer> {
    String text;

    public PartialProcessing(String text) {
        this.text = text;
    }

    // in the call method, get the rows and the size of the largest interval
    // return of the largest interval each row
    @Override
    public Integer call() throws Exception {
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
        return maxSize;
    }
}


