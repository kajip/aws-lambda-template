package org.kajip.aws;

import com.amazonaws.services.redshift.model.Tag;
import org.kajip.config.ClasspathProperties;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class SystemTags {

    public final static String SYSTEM_KEY = "X-System";

    public final static String ENVIRONMENT_KEY = "X-Environment";

    private final Properties properties;

    public SystemTags() {

        this.properties =new ClasspathProperties("lambda/redshift-tags.properties");

        properties.putIfAbsent(SYSTEM_KEY, "RedshiftFunction");
        properties.putIfAbsent(ENVIRONMENT_KEY, "Development");
    }

    public List<Tag> getTags() {
        return properties.entrySet().stream()
                .map(entry -> new Tag()
                        .withKey((String)entry.getKey())
                        .withValue((String)entry.getValue()))
                .collect(Collectors.toList());
    }


    public String getSystem() {
        return properties.getProperty(SYSTEM_KEY);
    }

    public String getEnvironment() {
        return properties.getProperty(ENVIRONMENT_KEY);
    }
}
