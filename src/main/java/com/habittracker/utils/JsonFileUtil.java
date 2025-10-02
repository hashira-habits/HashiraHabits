package com.habittracker.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Centralized JSON read/write with file locking.
 */
public class JsonFileUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static <T> T read(Path path, TypeReference<T> typeRef, T defaultValue) throws IOException {
        lock.readLock().lock();
        try {
            ensureParent(path);
            File file = path.toFile();
            if (!file.exists() || file.length() == 0) {
                return defaultValue;
            }
            return objectMapper.readValue(file, typeRef);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static <T> void write(Path path, T value) throws IOException {
        lock.writeLock().lock();
        try {
            ensureParent(path);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static Path resolveDataPath(String filename) {
        Path dataDir = Paths.get("data");
        return dataDir.resolve(filename);
    }

    private static void ensureParent(Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}
