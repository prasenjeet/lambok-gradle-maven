package com.example.lombok;

import com.example.lombok.demo.BuilderDemo;
import com.example.lombok.demo.LoggingDemo;
import com.example.lombok.demo.UtilityDemo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("================================================");
        log.info("  Lombok Features Demo — Gradle Project");
        log.info("================================================");

        BuilderDemo.run();
        log.info("");

        LoggingDemo.run();
        log.info("");

        UtilityDemo.run();
        log.info("");

        log.info("Done. Check the model/ and demo/ packages to see Lombok in action.");
    }
}
