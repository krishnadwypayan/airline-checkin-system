package org.example.approaches;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public abstract class BookingStrategy {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void book(final int airlineId) {
        final long startTime = System.currentTimeMillis();

        final List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            final int finalI = i;
            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> bookSeat(airlineId, finalI+1), executor);
            futures.add(future);
        }

        final CompletableFuture<Void> allCompletableFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        final CompletableFuture<List<Void>> listCompletableFuture = allCompletableFutures.thenApply(future -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
        try {
            listCompletableFuture.get();

            final double timeTaken = System.currentTimeMillis() - startTime;
            System.out.printf("\nCheck-In completed in %.3f sec\n\n", timeTaken/1000);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void bookSeat(final int airlineId, final int passengerId);
}
