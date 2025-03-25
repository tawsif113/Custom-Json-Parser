package com.mydesire.explore.customJsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private final List<JsonToken> tokens;
    private int pos = 0;

    public JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
    }

    public Object parse() {
        return parseValue();
    }

    private Object parseValue() {
        JsonToken token = tokens.get(pos);
        Object result = switch (token.type()) {
            case OPEN_BRACE -> parseObject();
            case OPEN_BRACKET -> parseArray();
            case STRING -> token.value();
            case NUMBER -> parseNumber(token.value());
            case TRUE -> true;
            case FALSE -> false;
            case NULL -> null;
            default -> throw new RuntimeException("Unexpected token: " + token);
        };

        // Increment position for primitive values
        if (!token.type().equals(TokenType.OPEN_BRACE) &&
                !token.type().equals(TokenType.OPEN_BRACKET)) {
            pos++;
        }

        return result;
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> obj = new HashMap<>();
        pos++; // Skip '{'
        while (pos < tokens.size()) {
            JsonToken token = tokens.get(pos);
            if (token.type() == TokenType.CLOSE_BRACE) {
                pos++;
                return obj;
            }
            String key = (String) parseValue(); // Key is always a string
            expectToken(TokenType.COLON);
            pos++; // Skip ':'
            Object value = parseValue();
            obj.put(key, value);
            if (tokens.get(pos).type() == TokenType.COMMA) {
                pos++;
            }
        }
        throw new RuntimeException("Unclosed object");
    }

    private List<Object> parseArray() {
        List<Object> array = new ArrayList<>();
        pos++; // Skip '['
        while (pos < tokens.size()) {
            if (tokens.get(pos).type() == TokenType.CLOSE_BRACKET) {
                pos++;
                return array;
            }
            array.add(parseValue());
            if (tokens.get(pos).type() == TokenType.COMMA) {
                pos++;
            }
        }
        throw new RuntimeException("Unclosed array");
    }

    private Number parseNumber(String value) {
        if (value.contains(".") || value.contains("e") || value.contains("E")) {
            return Double.parseDouble(value);
        } else {
            return Long.parseLong(value);
        }
    }

    private void expectToken(TokenType expected) {
        if (tokens.get(pos).type() != expected) {
            throw new RuntimeException("Expected " + expected + ", found " + tokens.get(pos));
        }
    }
}
