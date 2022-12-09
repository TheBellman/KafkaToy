package net.parttimepolymath.kafkatoy;

import java.util.Properties;

/**
 * convenience wrapper around the application properties with getter semantics.
 *
 * @author Robert Hook
 * @since 2022-11-27
 */
public final class ApplicationProperties {
    private static final Properties PROPERTIES = ResourceUtils.loadProperties("application.properties");

    /**
     * get the application name from application.name
     *
     * @return a non-null but possibly empty string.
     */
    public static String getAppName() {
        return PROPERTIES.getProperty("application.name", "");
    }

    /**
     * get the application version from application.version
     *
     * @return a non-null but possibly empty string.
     */
    public static String getAppVersion() {
        return PROPERTIES.getProperty("application.version", "");
    }

    /**
     * get the application build date from application.build.date
     *
     * @return a non-null but possibly empty string.
     */
    public static String getBuildDate() {
        return PROPERTIES.getProperty("application.build.date", "");
    }

    /**
     * get a client.id for the producer.
     * @return a non-null and non-empty string
     */
    public static String getProducerId() {
        return PROPERTIES.getProperty("kafka.producer.id", "Producer");
    }

    /**
     * get the default topic name
     *
     * @return a non-null but possibly empty string.
     */
    public static String getDefaultTopic() {
        return PROPERTIES.getProperty("default.topic", "");
    }

    /**
     * get the consumer group id.
     *
     * @return a non-null but possibly empty string.
     */
    public static String getGroupId() {
        return PROPERTIES.getProperty("kafka.group.id", "");
    }
    /**
     * get the bootstrap server:port for connecting to Kafka
     *
     * @return a non-null but possibly empty string.
     */
    public static String getBootstrap() {
        return PROPERTIES.getProperty("kafka.boostrap", "localhost:9092");
    }

    public static String getSchemaRegistryUrl() {
        return PROPERTIES.getProperty("schema.registry.url", "http://localhost:8081");
    }
}
