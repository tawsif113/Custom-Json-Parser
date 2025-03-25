package com.mydesire.explore.customJsonParser;

import java.util.List;
import java.util.Map;

public class CustomJsonParserMain {
    public static void main(String[] args) {
        String json = "{ \"name\": \"John\", \"age\": 30, \"hobbies\": [\"coding\", \"music\"] }";

        JsonTokenizer tokenizer = new JsonTokenizer(json);
        List<JsonToken> tokens = tokenizer.tokenize();

        JsonParser parser = new JsonParser(tokens);
        Map<String, Object> result = (Map<String, Object>) parser.parse();

        System.out.println(result); // {name=John, age=30, hobbies=[coding, music]}
    }
}
