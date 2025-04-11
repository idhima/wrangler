package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String value) {
        super(value);
        this.bytes = parseToBytes(value);
    }

    private long parseToBytes(String input) {
        input = input.toLowerCase().trim();
        if (input.endsWith("kb")) return (long)(Double.parseDouble(input.replace("kb", "")) * 1024);
        if (input.endsWith("mb")) return (long)(Double.parseDouble(input.replace("mb", "")) * 1024 * 1024);
        if (input.endsWith("gb")) return (long)(Double.parseDouble(input.replace("gb", "")) * 1024 * 1024 * 1024);
        return Long.parseLong(input);
    }

    public long getBytes() {
        return bytes;
    }
}
