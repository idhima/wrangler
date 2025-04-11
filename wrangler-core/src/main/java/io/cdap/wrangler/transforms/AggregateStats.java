package io.cdap.wrangler.transforms;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.DirectiveName;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

import java.util.ArrayList;
import java.util.List;

/**
 * A directive to aggregate byte size and time duration values.
 */
@DirectiveName(name = "aggregate-stats", usage = "aggregate-stats :sizeColumn :timeColumn outputSize outputTime")
public class AggregateStats implements Directive {
  private String sizeCol;
  private String timeCol;
  private String outputSizeCol;
  private String outputTimeCol;

  private long totalBytes = 0;
  private long totalMillis = 0;
  private boolean finalized = false;

  @Override
  public UsageDefinition define() {
    return UsageDefinition.builder("aggregate-stats")
      .define("sizeCol", TokenType.COLUMN_NAME)
      .define("timeCol", TokenType.COLUMN_NAME)
      .define("outputSizeCol", TokenType.COLUMN_NAME)
      .define("outputTimeCol", TokenType.COLUMN_NAME)
      .build();
  }

  @Override
  public void initialize(DirectiveContext ctx) {
    sizeCol = ctx.getArgumentValue("sizeCol");
    timeCol = ctx.getArgumentValue("timeCol");
    outputSizeCol = ctx.getArgumentValue("outputSizeCol");
    outputTimeCol = ctx.getArgumentValue("outputTimeCol");
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context) {
    if (finalized) return new ArrayList<>();

    for (Row row : rows) {
      Object sizeVal = row.getValue(sizeCol);
      Object timeVal = row.getValue(timeCol);

      if (sizeVal instanceof String) {
        ByteSize bs = new ByteSize((String) sizeVal);
        totalBytes += bs.getBytes();
      }

      if (timeVal instanceof String) {
        TimeDuration td = new TimeDuration((String) timeVal);
        totalMillis += td.getMilliseconds();
      }
    }

    finalized = true;

    List<Row> result = new ArrayList<>();
    Row out = new Row();
    out.add(outputSizeCol, totalBytes / (1024.0 * 1024.0)); // MB
    out.add(outputTimeCol, totalMillis / 1000.0); // Seconds
    result.add(out);

    return result;
  }
}
