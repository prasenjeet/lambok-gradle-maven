package com.example.lombok.demo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class UtilityDemo {

    private int counter = 0;

    public static void run() {
        log.info("=== @NonNull / @Cleanup / @SneakyThrows / @Synchronized Demo ===");

        UtilityDemo demo = new UtilityDemo();
        demo.demonstrateNonNull();
        demo.demonstrateCleanup();
        demo.demonstrateSneakyThrows();
        demo.demonstrateSynchronized();
    }

    // --- @NonNull ---
    // Lombok inserts a null check at the top of the method (or constructor):
    //   if (name == null) throw new NullPointerException("name is marked @NonNull but is null");
    private void demonstrateNonNull() {
        log.info("--- @NonNull ---");
        try {
            printName(null);
        } catch (NullPointerException e) {
            log.warn("@NonNull blocked null argument: {}", e.getMessage());
        }
        printName("Alice");
    }

    private void printName(@NonNull String name) {
        log.info("Name: {}", name);
    }

    // --- @Cleanup ---
    // Equivalent to try-with-resources: guarantees close() is called even on exception.
    // @SneakyThrows here handles the checked IOException from stream read/write.
    @SneakyThrows
    private void demonstrateCleanup() {
        log.info("--- @Cleanup ---");

        @Cleanup InputStream in = new ByteArrayInputStream("Hello, Lombok!".getBytes());
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buf = new byte[8];
        int read;
        while ((read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
        }
        log.info("@Cleanup read: \"{}\"  (streams auto-closed after this block)", out);
    }

    // --- @SneakyThrows ---
    // Lets you throw a checked exception without declaring it in the method signature.
    // Useful when implementing an interface that doesn't allow checked exceptions.
    // Uses Java's type-erasure trick — the exception is NOT wrapped, just re-thrown as-is.
    private void demonstrateSneakyThrows() {
        log.info("--- @SneakyThrows ---");
        String result = readFile("nonexistent-file.txt");
        log.info("readFile result: {}", result);
    }

    @SneakyThrows(IOException.class)
    private String readFile(String path) {
        // No 'throws IOException' needed in the signature, even though we can throw one
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
        } catch (IOException e) {
            return "(file not found: " + path + ")";
        }
    }

    // --- @Synchronized ---
    // Safer than the 'synchronized' keyword on a method.
    // Generates a private $lock field (Object[]) and wraps the body in synchronized($lock).
    // The 'synchronized' keyword locks on 'this', which external code can also lock on,
    // risking deadlocks. @Synchronized uses a hidden, private lock instead.
    @Synchronized
    private void demonstrateSynchronized() {
        log.info("--- @Synchronized ---");
        counter++;
        log.info("Counter (thread-safe via @Synchronized): {}", counter);
    }
}
