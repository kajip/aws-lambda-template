package org.kajip.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClasspathProperties extends Properties {

    public ClasspathProperties(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try(InputStream in = classLoader.getResourceAsStream(path)) {
            load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
