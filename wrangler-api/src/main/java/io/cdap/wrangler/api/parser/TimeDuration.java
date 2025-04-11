package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long milliseconds;

    public TimeDuration(String value) {
        super(value);
        this.milliseconds = parseToMillis(value);
    }

    private long parseToMillis(String input) {
        input = input.toLowerCase().trim();
        if (input.endsWith("ms")) return (long)(Double.parseDouble(input.replace("ms", "")));
        if (input.endsWith("s")) return (long)(Double.parseDouble(input.replace("s", "")) * 1000);
        if (input.endsWith("min")) return (long)(Double.parseDouble(input.replace("min", "")) * 60000);
        if (input.endsWith("h")) return (long)(Double.parseDouble(input.replace("h", "")) * 3600000);
        return 0;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
