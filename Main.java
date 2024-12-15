import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        long startTime = System.currentTimeMillis();
        
        CompletableFuture<int[]> generateSequence = CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating random sequence of 20 natural numbers...");
            return new Random().ints(20, 1, 101).toArray();
        });
        
        CompletableFuture<Integer> findMinSum = generateSequence.thenApplyAsync(sequence -> {
            System.out.println("Calculating minimum of consecutive sums...");
            return IntStream.range(0, sequence.length - 1).map(i -> sequence[i] + sequence[i+1]).min().orElseThrow();
        });
        
        CompletableFuture<Void> displayResults = findMinSum.thenAcceptAsync(minSum -> {
            System.out.println("Minimum of consecutive sums: " + minSum);
        });
        
        generateSequence.thenAcceptAsync(sequence -> {
            System.out.println("Generated Array: " + Arrays.toString(sequence));
        });
        
        displayResults.get();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - startTime) + "ms");
    }
}
