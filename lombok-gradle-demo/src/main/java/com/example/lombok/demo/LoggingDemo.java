package com.example.lombok.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * Demonstrates Lombok's logging annotations.
 *
 * Each annotation injects a static 'log' field — choose the one that matches your logging framework:
 *
 *   @Slf4j       → org.slf4j.Logger          (most common; works with Logback, Log4j2, etc.)
 *   @Log         → java.util.logging.Logger   (JDK built-in)
 *   @Log4j2      → org.apache.logging.log4j.Logger
 *   @CommonsLog  → org.apache.commons.logging.Log
 *   @Flogger     → com.google.common.flogger.FluentLogger
 *   @XSlf4j      → org.slf4j.ext.XLogger
 *
 * Generated code equivalent:
 *   private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoggingDemo.class);
 */
@Slf4j
public class LoggingDemo {

    public static void run() {
        log.info("=== @Slf4j Logging Demo ===");

        // Five standard log levels, from finest to most severe
        log.trace("TRACE - function calls, loop iterations (very verbose)");
        log.debug("DEBUG - variable values, flow control, diagnostics");
        log.info("INFO  - significant events: startup, user actions, milestones");
        log.warn("WARN  - potential issues: deprecated API, near-limit conditions");
        log.error("ERROR - failures that don't stop execution");

        // Use {} placeholders instead of string concatenation — lazy evaluation,
        // no String built if the log level is disabled
        String user = "Alice";
        int itemCount = 5;
        log.info("Parameterized: user={}, items={}", user, itemCount);

        // Log exceptions with full stack trace as the last argument
        try {
            int result = Integer.parseInt("not-a-number");
        } catch (NumberFormatException e) {
            log.error("Parse failed for input '{}': {}", "not-a-number", e.getMessage(), e);
        }

        log.info("Logging demo complete.");
    }
}
