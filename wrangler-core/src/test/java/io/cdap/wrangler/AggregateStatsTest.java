package io.cdap.wrangler;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.transforms.AggregateStats;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
  @Test
  public void testAggregation() throws Exception {
    Row row1 = new Row("size", "10MB", "duration", "2s");
    Row row2 = new Row("size", "5MB", "duration", "3s");

    AggregateStats directive = new AggregateStats();
    directive.initialize(new MockDirectiveContext("aggregate-stats", "size", "duration", "totalSize", "totalTime"));
    List<Row> result = directive.execute(Arrays.asList(row1, row2), null);

    Assert.assertEquals(1, result.size());
    Row output = result.get(0);
    Assert.assertEquals(15.0, (double) output.getValue("totalSize"), 0.001);
    Assert.assertEquals(5.0, (double) output.getValue("totalTime"), 0.001);
  }
}

