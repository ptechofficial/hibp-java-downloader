package com.hibpdownloader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HibpDownloader {
    private static final String BASE_URL = "https://api.pwnedpasswords.com/range/";
    private static final int MAX_PARALLELISM = 8;
    private static final String OUTPUT_DIR = "hashedPasswords";

    public static void main(String[] args) {
        // Create the output directory if it doesn't exist
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        ExecutorService executor = Executors.newFixedThreadPool(MAX_PARALLELISM);

        for (int i = 0; i < 1024 * 1024; i++) {
            final int hashRange = i;
            executor.submit(() -> {
                try {
                    fetchAndSaveHashRange(hashRange);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fetchAndSaveHashRange(int hashRange) throws IOException, InterruptedException {
        String range = getHashRange(hashRange);
        URI uri = URI.create(BASE_URL + range);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            String fileName = OUTPUT_DIR + File.separator + range + ".txt";
            File outputFile = new File(fileName);

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))) {
                String content = new String(response.body().readAllBytes());
                writer.write(content);
            }

            // Log the downloaded range
            System.out.println("Downloaded and saved hash range: " + range);
        } else {
            System.err.println("Failed to fetch range " + range + ": HTTP " + response.statusCode());
        }
    }

    private static String getHashRange(int i) {
        return String.format("%05X", i).substring(0, 5);
    }
}
