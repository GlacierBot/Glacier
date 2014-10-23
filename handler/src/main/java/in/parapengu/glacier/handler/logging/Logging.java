package in.parapengu.glacier.handler.logging;

import java.util.HashMap;
import java.util.Map;

import static in.parapengu.glacier.handler.logging.LogLevel.INFO;

public class Logging {

    private static Map<String, Logger> loggers = new HashMap<>();
    private static LogWriter writer = new LogWriter();
    private static Logger global = getLogger((String) null);

    public static Logger getLogger(String name, Logger parent, LogLevel level) {
        Logger logger = new Logger(name, level);
        logger.setParent(parent);
        loggers.put(name, logger);
        return logger;
    }

    public static Logger getLogger(Class<?> clazz, Logger parent, LogLevel level) {
        return getLogger(clazz.getSimpleName(), parent, level);
    }

    public static Logger getLogger(String name, Logger parent) {
        return getLogger(name, parent, INFO);
    }

    public static Logger getLogger(Class<?> clazz, Logger parent) {
        return getLogger(clazz.getSimpleName(), parent);
    }

    public static Logger getLogger(String name, LogLevel level) {
        return getLogger(name, global, level);
    }

    public static Logger getLogger(Class<?> clazz, LogLevel level) {
        return getLogger(clazz.getSimpleName(), level);
    }

    public static Logger getLogger(String name) {
        if (loggers != null && loggers.containsKey(name)) {
            return loggers.get(name);
        }

        return getLogger(name, global);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getSimpleName());
    }

    public static Logger getLogger() {
        return global;
    }

    public static LogWriter getWriter() {
        return writer;
    }

}
