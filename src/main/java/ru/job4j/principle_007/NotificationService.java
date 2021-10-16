package ru.job4j.principle_007;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );
    private final Map<String, List<String>> cache = new HashMap<>();

    public NotificationService() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        fire();
                    }
                },
                5000L, 5000L
        );
    }

    public void notify(String email, String text) {
        if (!cache.containsKey(email)) {
            cache.put(email, new ArrayList<>());
        }
        cache.get(email).add(text);
    }

    private void fire() {
        for (String key : new HashSet<>(cache.keySet())) {
            List<String> body = cache.remove(key);
            StringBuilder text = new StringBuilder();
            body.forEach(text::append);
            send(key, text.toString());
        }
    }

    private void send(String subject, String body) {
        pool.submit(() -> {
            /* Логика рассылки. */
        });
    }

    public void shutdown() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
