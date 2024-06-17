package org.plivo.config;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import java.util.Map;

public class DotEnvContainer {
    private static class DotEnvContainerInternal {
        private static final Dotenv INSTANCE = Dotenv.configure()
            .ignoreIfMissing()
            .load();
    }

    public static String get(String property) {
        return DotEnvContainerInternal.INSTANCE.get(property);
    }

    public static Map<String, String> properties() {
        return DotEnvContainerInternal.INSTANCE
            .entries()
            .stream()
            .collect(toImmutableMap(DotenvEntry::getKey, DotenvEntry::getValue));
    }
}