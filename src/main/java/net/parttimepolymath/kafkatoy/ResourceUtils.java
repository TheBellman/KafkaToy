package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Utility class for dealing with resource files from the JAR or from code base during tests.
 *
 * @author Robert Hook
 * @since 2022-11-27
 */
@Slf4j
public final class ResourceUtils {
    /**
     * load a set of Properties from a nominated file in the JAR or code base.
     *
     * @param fileName the name of the file to load.
     * @return a non-null set of properties. This may be empty if the file cannot be loaded.
     */
    public static @NotNull Properties loadProperties(final @NotNull String fileName) {
        Properties prop = new Properties();

        getResourceAsStream(fileName).ifPresent(inStream -> {
            try {
                prop.load(inStream);
            } catch (IOException e) {
                log.warn("Could not load properties from '{}'", fileName);
            }
        });
        return prop;
    }

    /**
     * load a file from the JAR or code base as a list of strings (if possible).
     *
     * @param fileName the name of the file to load
     * @return a non-null but possibly empty list of strings
     */
    public static List<String> readResourceFileAsList(final @NotNull String fileName) {
        return getResourceAsStream(fileName).map(inputStream -> new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    /**
     * create an input stream from a file in the JAR or code base.
     *
     * @param fileName the name of the file to treat as an input stream. This will be prefixed with "/" if it is not
     *                 already.
     * @return a non-null Optional
     */
    public static Optional<InputStream> getResourceAsStream(final @NotNull String fileName) {
        return Optional.ofNullable(ResourceUtils.class.getResourceAsStream(StringUtils.prependIfMissing(fileName,
                "/")));
    }
}
