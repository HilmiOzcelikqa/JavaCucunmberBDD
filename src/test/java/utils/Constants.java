package utils;

import java.time.Duration;

public class Constants {

    public static final String CONFIGURATION_FILEPATH = System.getProperty("user.dir")+"/src/test/resources/config/config.properties";
    public static final Duration IMPLICIT_WAIT = Duration.ofSeconds(10);
    public static final Duration EXPLICIT_WAIT = Duration.ofSeconds(20);
    public static final String SCREENSHOT_FILEPATH = System.getProperty("user.dir")+"/screenshots/";
}