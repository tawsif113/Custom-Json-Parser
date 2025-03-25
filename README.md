Custom JSON Parser in Java



A lightweight JSON parser implementation in pure Java without external dependencies. Built for educational purposes to understand JSON parsing fundamentals.

Features

🧩 Tokenization (lexical analysis)

🌳 Syntax parsing to Java Map/List structures

✅ Supports:

Objects {}

Arrays []

Strings " "

Numbers (integer/decimal)

Booleans (true/false)

null values

🚨 Basic error handling

Usage

Basic Parsing

String json = "{ \"name\": \"John\", \"age\": 30 }";

// Tokenization
JsonTokenizer tokenizer = new JsonTokenizer(json);
List<JsonToken> tokens = tokenizer.tokenize();

// Parsing
JsonParser parser = new JsonParser(tokens);
Map<String, Object> result = (Map<String, Object>) parser.parse();

System.out.println(result.get("name")); // Output: John

Spring Boot Integration

Implement custom HttpMessageConverter:

@Component
public class CustomJsonConverter extends AbstractHttpMessageConverter<Object> {
    // Implementation from src/main/java/.../converter/
}

Use in controller:

@PostMapping("/data")
public ResponseEntity<?> handleData(@RequestBody Map<String, Object> parsedJson) {
    // Work with parsed data
    return ResponseEntity.ok(parsedJson);
}

Limitations

⚠️ This is a learning implementation, not production-ready. Missing:

Escaped characters in strings

Unicode support

Scientific notation in numbers

Full JSON spec compliance

For production use, consider Jackson or Gson.

Contributing

PRs welcome for:

Error handling improvements

Additional JSON features

Performance optimizations



