package net.twitter.provider;

import net.twitter.infra.JwtParser;

import java.util.function.Consumer;
import java.util.function.Function;

public class JwtParserProvider {

    private static JwtParser parser;

    public static void setParser(JwtParser parser) {
        JwtParserProvider.parser = parser;
    }

    public static <T> T withJwtParser(Function<JwtParser, T> function) {
        return function.apply(parser);
    }

    public static void useJwtParser(Consumer<JwtParser> consumer) {
        consumer.accept(parser);
    }
}
