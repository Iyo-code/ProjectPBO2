package org.slf4j;

public class LoggerFactory {
    public static Logger getLogger(String name) {
        return new NoOpLogger();
    }

    public static Logger getLogger(Class<?> clazz) {
        return new NoOpLogger();
    }
}