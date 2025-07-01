package org.slf4j;

public interface Logger {
    void trace(String msg);

    void debug(String msg);

    void info(String msg);

    void warn(String msg);

    void error(String msg);

    boolean isTraceEnabled();

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();
}